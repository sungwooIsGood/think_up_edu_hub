package com.edu.presentation.controller;

import com.edu.application.UserService;
import com.edu.domain.dto.UserLoginRequest;
import com.edu.domain.dto.UserSignUpRequest;
import com.edu.entity.BasicResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;

    @PostMapping("/sign-up")
    public BasicResponse signUp(UserSignUpRequest userSignUpRequest){

        userService.signUp(userSignUpRequest);

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

        return BasicResponse.builder()
                .status(HttpStatus.OK.value())
                .build();
    }
}
