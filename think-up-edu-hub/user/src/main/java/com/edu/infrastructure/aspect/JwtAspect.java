package com.edu.infrastructure.aspect;

import com.edu.application.UserService;
import com.edu.domain.dto.JwtPayload;
import com.edu.domain.dto.LoginVerifyItem;
import com.edu.domain.enums.TokenStateType;
import com.edu.domain.service.JwtAuthService;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

@Component
@Aspect
@RequiredArgsConstructor
public class JwtAspect {

    private final UserService userService;
    private final JwtAuthService jwtAuthService;
    private final Logger log = LoggerFactory.getLogger(this.getClass().getSimpleName());

    @Around("@annotation(JwtVerification)")
    public Object jwtVerification(ProceedingJoinPoint jp) {

        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        Object[] objects = jp.getArgs().clone(); // method의 파라미터 값 클론

        String authorizationToken = request.getHeader("Authorization");
        String accessToken = jwtAuthService.getAccessTokenByHeader(authorizationToken);
        log.info("요청 된 accessToken: {}", accessToken);
        String newAccessToken = "";

        if (Objects.nonNull(accessToken)) {
            JwtPayload payloadByJwtDecode = jwtAuthService.getPayloadByJwtDecode(accessToken);
            boolean accessTokenExpiredIsBefore = jwtAuthService.validateTokenExpiredDate(accessToken);

            if(!accessTokenExpiredIsBefore){
                newAccessToken = userService.resetAccessTokenByRefreshToken(authorizationToken);
            }

        }


    }

}