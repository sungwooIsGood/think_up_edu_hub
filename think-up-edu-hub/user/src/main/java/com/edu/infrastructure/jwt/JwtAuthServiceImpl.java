package com.edu.infrastructure.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.edu.domain.dto.JwtPayload;
import com.edu.domain.dto.JwtTokenGroup;
import com.edu.domain.dto.UserLoginItem;
import com.edu.domain.service.JwtAuthService;
import com.edu.domain.repository.UserJRepository;
import com.google.gson.Gson;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import net.bytebuddy.asm.Advice;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class JwtAuthServiceImpl implements JwtAuthService {

    @Value("${jwt.secret.key")
    private String secretKey;
    private final UserJRepository userJRepository;
    private final Logger log = LoggerFactory.getLogger(this.getClass().getSimpleName());

    @Override
    public JwtTokenGroup createAccAndRefreshToken(Long userId){
        return createJwtToken(userId);
    }

    private JwtTokenGroup createJwtToken(Long userId) {

        LocalDateTime now = LocalDateTime.now();

        String accessJwtToken = createAccessJwtToken(userId,now);
        String refreshJwtToken = createRefreshJwtToken(userId,now);

        return JwtTokenGroup.createdJwtTokenGroup(accessJwtToken,refreshJwtToken);
    }

    private String createAccessJwtToken(Long userId,LocalDateTime now) {

        return JWT.create()
                .withIssuer("ian") // 토큰 발급자
                .withIssuedAt(Date.from(now.toInstant(ZoneOffset.UTC)).toInstant()) // 토큰 생성 시간
                .withExpiresAt(Date.from(now.plusDays(1).toInstant(ZoneOffset.UTC)).toInstant()) // 토큰 만료 시간
                .withClaim("userId", userId)
                .sign(Algorithm.HMAC256(secretKey));// 암호화 기법
    }

    private String createRefreshJwtToken(Long userId,LocalDateTime now) {

        return JWT.create()
                .withIssuer("ian") // 토큰 발급자
                .withIssuedAt(Date.from(now.toInstant(ZoneOffset.UTC)).toInstant()) // 토큰 생성 시간
                .withExpiresAt(Date.from(now.plusDays(15).toInstant(ZoneOffset.UTC)).toInstant()) // 토큰 만료 시간
                .withClaim("userId", userId)
                .sign(Algorithm.HMAC256(secretKey));// 암호화 기법
    }

    @Override
    public String getAccessTokenByHeader(String authorizationToken) {

        String expiredAccessToken= "";

        if(Objects.isNull(authorizationToken)){
            throw new IllegalStateException("헤더에 토큰이 존재하지 않습니다.");
        }

        try {
            expiredAccessToken = authorizationToken.split("Bearer ")[1];
        }catch (ArrayIndexOutOfBoundsException e){
            log.info("헤더를 통해 값이 들어왔지만 예외 발생, authorizationToken: {}",authorizationToken);
            throw new IllegalStateException("헤더에 토큰이 존재하지 않거나 값이 옳바르지 않습니다.");
        }

        return expiredAccessToken;
    }

    @Override
    public JwtPayload getPayloadByJwtDecode(String accessToken) {

        String payload = getJwtPayload(accessToken);
        String decodedPayload = decodeBase64Payload(payload);
        return getJwtPayloadData(decodedPayload);
    }

    private String getJwtPayload(String expiredAccessToken) {
        String payload = "";
        try{
            Algorithm ALGORITHM = Algorithm.HMAC256(secretKey);
            JWTVerifier verifier = JWT.require(ALGORITHM).build(); // 토큰 검증을 위한 verifier 생성
            DecodedJWT jwt = verifier.verify(expiredAccessToken); // 토큰 검증 및 디코딩
            payload = jwt.getPayload();
        } catch (JWTVerificationException e){
            // 토큰이 유효하지 않을 때 실행할 로직을 여기에 추가
            log.info("유효하지 않는 access token 값이 들어왔습니다.: 입력값: {}",expiredAccessToken);
            throw new IllegalStateException("유효하지 않는 토큰이 들어왔습니다.");
        }

        return payload;
    }

    private String decodeBase64Payload(String payload) {
        return new String(java.util.Base64.getUrlDecoder().decode(payload.getBytes()));
    }

    private JwtPayload getJwtPayloadData(String decodedPayload){
        Gson gson = new Gson();
        return gson.fromJson(decodedPayload,JwtPayload.class);
    }

    @Override
    public boolean validateTokenExpiredDate(String accessToken){
        Algorithm ALGORITHM = Algorithm.HMAC256(secretKey);
        JWTVerifier verifier = JWT.require(ALGORITHM).build(); // 토큰 검증을 위한 verifier 생성
        DecodedJWT jwt = verifier.verify(accessToken); // 토큰 검증 및 디코딩
        return jwt.getExpiresAt().after(new Date());
    }

}
