package com.edu.infrastructure.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.edu.domain.dto.JwtTokenGroup;
import com.edu.domain.dto.UserLoginItem;
import com.edu.domain.service.JwtAuthService;
import com.edu.domain.repository.UserJRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;

@Service
@RequiredArgsConstructor
public class JwtAuthServiceImpl implements JwtAuthService {

    @Value("${jwt.secret.key")
    private String secretKey;
    private final UserJRepository userJRepository;

    @Override
    public String authenticateLogin(UserLoginItem userLoginItem){
        JwtTokenGroup jwtToken = createJwtToken(userLoginItem.getUserId());
        return jwtToken.getAccessToken();
    }

    private JwtTokenGroup createJwtToken(Long userId) {
        String accessJwtToken = createAccessJwtToken(userId);
        String refreshJwtToken = createRefreshJwtToken(userId);

        return JwtTokenGroup.createdJwtTokenGroup(accessJwtToken,refreshJwtToken);
    }

    private String createAccessJwtToken(Long userId) {

        LocalDateTime now = LocalDateTime.now();

        return JWT.create()
                .withIssuer("ian") // 토큰 발급자
                .withIssuedAt(Date.from(now.toInstant(ZoneOffset.UTC)).toInstant()) // 토큰 생성 시간
                .withExpiresAt(Date.from(now.plusDays(5).toInstant(ZoneOffset.UTC)).toInstant()) // 토큰 만료 시간
                .withClaim("userId", userId)
                .sign(Algorithm.HMAC256(secretKey));// 암호화 기법
    }

    private String createRefreshJwtToken(Long userId) {

        LocalDateTime now = LocalDateTime.now();

        return JWT.create()
                .withIssuer("ian") // 토큰 발급자
                .withIssuedAt(Date.from(now.toInstant(ZoneOffset.UTC)).toInstant()) // 토큰 생성 시간
                .withExpiresAt(Date.from(now.plusDays(15).toInstant(ZoneOffset.UTC)).toInstant()) // 토큰 만료 시간
                .withClaim("userId", userId)
                .sign(Algorithm.HMAC256(secretKey));// 암호화 기법
    }
}
