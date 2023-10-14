package com.edu.presentation.controller;

import com.edu.application.UserService;
import com.edu.domain.dto.LoginVerifyItem;
import com.edu.domain.dto.UserLoginItem;
import com.edu.domain.dto.UserLoginRequest;
import com.edu.domain.dto.UserSignUpRequest;
import com.edu.entity.BasicResponse;
import com.edu.infrastructure.aspect.JwtHeaderReader;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.Basic;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;
    private final Logger log = LoggerFactory.getLogger(this.getClass().getSimpleName());

    /**
     * 회원가입
     */
    @PostMapping("/sign-up")
    public BasicResponse signUp(UserSignUpRequest userSignUpRequest){

        userService.signUp(userSignUpRequest);

        return BasicResponse.builder()
                .status(HttpStatus.OK.value())
                .build();

    }

    /**
     * 회원탈퇴
     */
    @JwtHeaderReader
    @DeleteMapping("/sign-out")
    public BasicResponse signOut(String password,
                                 HttpServletResponse response,
                                 String authorizationToken){

        userService.signOut(password, authorizationToken);

        // 클라이언트가 헤더의 access token을 쿠키에 저장해놓고 쓰기 때문에, cookie를 삭제한다.
        String accessToken = authorizationToken.split("Bearer ")[1];
        Cookie cookie = new Cookie("acc", accessToken);
        cookie.setHttpOnly(true);
        cookie.setSecure(true);
        cookie.setMaxAge(0); // 0초
        cookie.setPath("/"); // 쿠키가 적용될 위치
        response.addCookie(cookie);

        return BasicResponse.builder()
                .status(HttpStatus.OK.value())
                .build();
    }

    /**
     * 로그인
     */
    @PostMapping("/login")
    public BasicResponse login(UserLoginRequest userLoginRequest, HttpServletResponse response){

        String accessToken = userService.login(userLoginRequest);

        // Access-Control-Expose-Headers는 JavaScript가 "Authorization" 헤더에 액세스할 수 있도록 허용하는 역할
        response.addHeader("Access-Control-Expose-Headers", "Authorization");
        response.addHeader("Authorization", String.format("Bearer %s", accessToken));

        log.info("발급된 access token: {}",accessToken);
        return BasicResponse.builder()
                .status(HttpStatus.OK.value())
                .build();
    }

    /**
     * 로그아웃
     */
    @JwtHeaderReader
    @PostMapping("/logout")
    public BasicResponse logout(HttpServletResponse response,String authorizationToken){

        userService.logout(authorizationToken);

        // 클라이언트가 헤더의 access token을 쿠키에 저장해놓고 쓰기 때문에, cookie를 삭제한다.
        String accessToken = authorizationToken.split("Bearer ")[1];
        Cookie cookie = new Cookie("acc", accessToken);
        cookie.setHttpOnly(true);
        cookie.setSecure(true);
        cookie.setMaxAge(0); // 0초
        cookie.setPath("/"); // 쿠키가 적용될 위치
        response.addCookie(cookie);

        return BasicResponse.builder()
                .status(HttpStatus.OK.value())
                .build();
    }

    /**
     * 만료 access 토큰 가져왔을 시 refresh token으로 access token 재발급 및 refresh token 재발급
     */
    @JwtHeaderReader
    @PostMapping("/reset/token")
    public BasicResponse resetRefreshToken(HttpServletResponse response,String authorizationToken){

        String accessToken = userService.resetAccessTokenByRefreshToken(authorizationToken);

        response.addHeader("Access-Control-Expose-Headers", "Authorization");
        response.addHeader("Authorization", String.format("Bearer %s", accessToken));

        log.info("발급된 access token: {}",accessToken);
        return BasicResponse.builder()
                .status(HttpStatus.OK.value())
                .build();
    }

    /**
     * 토큰을 통해 로그인이 되어있는지 확인 - 클라이언트에서 페이지 이동 시 해당 API를 호출하도록 설정
     *
     * 로그인 되었을 때
     * 1. access token 가져온 후 만료 되었는지 확인해본다.
     * 3. 만료 되었다면, 새롭게 acc, refresh 토큰을 발급해준다.
     *
     * 로그아웃 되었을 때
     * 1. access token 가져와보지만 안에 값이 없기 때문에 로그인 유저가 아닌 것을 알 수 있다.
     */
    @JwtHeaderReader
    @PostMapping("/login-verify")
    public BasicResponse verifyLogin(HttpServletResponse response,String authorizationToken){

        Map<String,Object> responseData = new HashMap<>();

        LoginVerifyItem loginVerifyItem = userService.verifyLogin(authorizationToken);

        response.addHeader("Access-Control-Expose-Headers", "Authorization");
        response.addHeader("Authorization", String.format("Bearer %s", loginVerifyItem.getAccessToken()));
        responseData.put("loginVerifyResult",loginVerifyItem);

        loginVerifyItem.putAccessToken("hidden");

        return BasicResponse.builder()
                .status(HttpStatus.OK.value())
                .data(responseData)
                .build();
    }
}
