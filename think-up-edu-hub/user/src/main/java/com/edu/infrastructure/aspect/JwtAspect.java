package com.edu.infrastructure.aspect;

import com.edu.application.UserService;
import com.edu.domain.dto.JwtVerifyResultItem;
import com.edu.domain.dto.JwtPayload;
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
import javax.servlet.http.HttpServletResponse;
import java.util.Objects;

@Component
@Aspect
@RequiredArgsConstructor
public class JwtAspect {

    private final UserService userService;
    private final JwtAuthService jwtAuthService;
    private final Logger log = LoggerFactory.getLogger(this.getClass().getSimpleName());

    /**
     * 해당 AOP는 jwt 검증을 실시
     * - header에 auth 값이 없으면 비로그인
     * - 만료된 access token을 가져왔을 경우 access와 refresh token 재발급 후 header에 넣어주기
     * - 만료되지 않은 access token을 가져왔을 경우 그냥 로그인 사용자라고 반환
     * - 잘못된 auth 값을 가져왔을 경우 jwtAuthService에서 예외를 터트림. TODO 잠만, 예외는 터지되 controller에서 작동하지 않았기 때문에 정상적인지는 확인해야함. try/catch로 막자.
     */
    @Around("@annotation(JwtVerification)")
    public Object jwtVerification(ProceedingJoinPoint jp) throws Throwable {

        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        HttpServletResponse response = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();
        Object[] objects = jp.getArgs().clone(); // method의 파라미터 값 클론

        String authorizationToken = request.getHeader("Authorization");
        String accessToken = jwtAuthService.getAccessTokenByHeader(authorizationToken);
        log.info("요청 된 accessToken: {}", accessToken);

        JwtVerifyResultItem jwtVerifyResultItem = new JwtVerifyResultItem();
        String newAccessToken = null;

        if (Objects.nonNull(accessToken)) {
            JwtPayload payloadByJwtDecode = jwtAuthService.getPayloadByJwtDecode(accessToken);
            boolean IsBeforeAccessTokenExpired = jwtAuthService.validateTokenExpiredDate(accessToken);

            if(!IsBeforeAccessTokenExpired){
                newAccessToken = userService.resetAccessTokenByRefreshToken(authorizationToken);
                jwtVerifyResultItem = JwtVerifyResultItem.createJwtVerifyResultItemByNewAcc(payloadByJwtDecode,newAccessToken);
            }

            if(IsBeforeAccessTokenExpired){
                jwtVerifyResultItem = JwtVerifyResultItem.createJwtVerifyResultItemByValid(payloadByJwtDecode,newAccessToken);
            }

            if(jwtVerifyResultItem.isAccessTokenResetResult()){
                response.addHeader("Access-Control-Expose-Headers", "Authorization");
                response.addHeader("Authorization", String.format("Bearer %s", newAccessToken));
            }
        }

        objects[objects.length - 1] = jwtVerifyResultItem;
        return jp.proceed(objects);
    }


    @Around("@annotation(JwtHeaderReader)")
    public Object jwtHeaderReader(ProceedingJoinPoint jp) throws Throwable {

        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        Object[] objects = jp.getArgs().clone(); // method의 파라미터 값 클론

        String authorizationToken = request.getHeader("Authorization");

        objects[objects.length-1] = authorizationToken;

        return jp.proceed(objects); // 메서드 실행
    }

}
