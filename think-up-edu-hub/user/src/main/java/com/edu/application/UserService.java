package com.edu.application;

import com.edu.domain.dto.JwtTokenGroup;
import com.edu.domain.dto.UserLoginRequest;
import com.edu.domain.dto.UserLoginItem;
import com.edu.domain.dto.UserSignUpRequest;
import com.edu.domain.entity.User;
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
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserJRepository userJRepository;
    private final UserRepository userRepository;
    private final VerificationSignUpComponent verificationSignUpComponent;
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
        JwtTokenGroup jwtTokenGroup = jwtAuthService.authenticateLogin(userLoginItem);

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

    public String resetAccessTokenByRefreshToken(String authorizationToken){

        String expiredAccessToken = jwtAuthService.getExpiredAccessToken(authorizationToken);

        jwtAuthService.getExpiredAccessTokenByJwtDecode(expiredAccessToken);

        return null;

    }
}
