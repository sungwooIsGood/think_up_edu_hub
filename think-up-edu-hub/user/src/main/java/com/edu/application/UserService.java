package com.edu.application;

import com.edu.domain.dto.*;
import com.edu.domain.entity.User;
import com.edu.domain.enums.TokenStateType;
import com.edu.domain.repository.UserRepository;
import com.edu.domain.service.JwtAuthService;
import com.edu.domain.repository.UserJRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserJRepository userJRepository;
    private final UserRepository userRepository;
    private final VerificationSignUpService verificationSignUpComponent;
    private final PasswordEncoder passwordEncoder;
    private final JwtAuthService jwtAuthService;
    private final RedisTemplate<String, Object> redisTemplate;

    @Transactional
    public String login(UserLoginRequest userLoginRequest){

        // check login verification
        checkLoginRequestIsNull(userLoginRequest.getUsername(),userLoginRequest.getPassword());

        // check is same login
        UserLoginItem userLoginItem = userRepository.findByUsernameAndPassword(userLoginRequest.getUsername(), userLoginRequest.getPassword());

        // jwt 토큰발급
        JwtTokenGroup jwtTokenGroup = jwtAuthService.createAccAndRefreshToken(userLoginItem.getUserId());

        // refresh token - redis
        saveRefreshTokenForRedis(userLoginItem.getUserId(),jwtTokenGroup.getRefreshToken());
        return jwtTokenGroup.getAccessToken();
    }

    private void checkLoginRequestIsNull(String username, String password) {

        if(Objects.isNull(username)){
            throw new IllegalArgumentException("username 값이 비어있습니다.");
        }

        if(Objects.isNull(password)){
            throw new IllegalArgumentException("password 값이 비어있습니다.");
        }

    }

    private void saveRefreshTokenForRedis(Long userId, String refreshToken) {
        ValueOperations<String, Object> valueOptions = redisTemplate.opsForValue();
        valueOptions.set("refresh_token:" + userId,refreshToken,15, TimeUnit.DAYS);
    }

    @Transactional
    public boolean signUp(UserSignUpRequest userSignUpRequest) {
        boolean isSignUpOk = verificationSignUpComponent.verifyCanSignUp(userSignUpRequest);

        // 회원가입 로직
        if(isSignUpOk){
            String encodePassword = passwordEncoder.encode(userSignUpRequest.getPassword()); // 비밀번호 인코딩
            User user = userSignUpRequest.createdUser(encodePassword);
            userJRepository.save(user);
            return true;
        }

        throw new IllegalArgumentException("잘못된 값이 넘어왔습니다. 입력값: " + userSignUpRequest);
    }

    @Transactional
    public String resetAccessTokenByRefreshToken(String authorizationToken){
        String activeRefreshToken = getActiveRefreshToken(authorizationToken);
        return resetAccAndRefreshToken(activeRefreshToken);
    }

    private String getActiveRefreshToken(String authorizationToken) {

        try{
            String expiredAccessToken = jwtAuthService.getAccessTokenByHeader(authorizationToken);
            JwtPayload expiredAccessTokenPayloadByJwtDecode = jwtAuthService.getPayloadByJwtDecode(expiredAccessToken);
            return String.valueOf(getActiveRefreshToken(expiredAccessTokenPayloadByJwtDecode.getUserId()).orElseThrow(() ->
                    new IllegalStateException("redis의 활성화 되어있는 refresh 토큰이 없습니다.")
            ));
        } catch (Exception e){
            e.printStackTrace();
            throw new IllegalStateException("만료된 access 토큰을 가지고 refresh 토큰을 불러오면서 에러 발생");
        }

    }

    private String resetAccAndRefreshToken(String activeRefreshToken) {

        try{
            JwtPayload activeRefreshTokenPayloadByJwtDecode = jwtAuthService.getPayloadByJwtDecode(activeRefreshToken);
            JwtTokenGroup jwtTokenGroup = jwtAuthService.createAccAndRefreshToken(activeRefreshTokenPayloadByJwtDecode.getUserId());
            saveRefreshTokenForRedis(activeRefreshTokenPayloadByJwtDecode.getUserId(),jwtTokenGroup.getRefreshToken());
            return jwtTokenGroup.getAccessToken();
        } catch (Exception e){
            e.printStackTrace();
            throw new IllegalStateException("access 토큰과 refresh 토큰을 새로 만들면서 에러 발생");
        }

    }

    private Optional<Object> getActiveRefreshToken(Long userId) {
        return Optional.ofNullable(redisTemplate.opsForValue().get("refresh_token:" + userId));
    }

    @Transactional
    public void logout(String authorizationToken){
        JwtPayload jwtPayload = jwtAuthService.getPayloadByJwtDecode(authorizationToken);
        deleteRefreshToken(jwtPayload.getUserId()); // 레디스에서 기존 refresh 토큰 삭제
    }

    private void deleteRefreshToken(Long userId) {
        redisTemplate.delete("refresh_token:" + userId);
    }

    @Transactional
    public LoginVerifyItem verifyLogin(String authorizationToken) {

        if(Objects.nonNull(authorizationToken)){

            String accessToken = jwtAuthService.getAccessTokenByHeader(authorizationToken);
            boolean accessTokenExpiredBefore = jwtAuthService.validateTokenExpiredDate(accessToken);
            TokenStateType tokenStateType;

            if(!accessTokenExpiredBefore){
                accessToken = resetAccAndRefreshToken(authorizationToken); // 재발급 받은 acc 토큰
                tokenStateType = TokenStateType.TOKEN_EXPIRE;
            } else{
                tokenStateType = TokenStateType.TOKEN_VALID;
            }

            JwtPayload payload = jwtAuthService.getPayloadByJwtDecode(accessToken);
            LoginVerifyItem loginVerifyItem = userRepository.findById(payload.getUserId());
            loginVerifyItem.putAccessToken(accessToken);
            loginVerifyItem.putTokenStateType(tokenStateType);

            return loginVerifyItem;
        }

        if(Objects.isNull(authorizationToken)){
            LoginVerifyItem loginVerifyItem = new LoginVerifyItem();
            loginVerifyItem.putTokenStateType(TokenStateType.TOKEN_IS_NOT_VALID);

            return loginVerifyItem;
        }

        throw new IllegalStateException("예상치 못한 예외 케이스가 발생 authorizationToken: " + authorizationToken);

    }
}
