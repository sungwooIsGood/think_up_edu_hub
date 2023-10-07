package com.edu.presentation.controller;

import com.edu.application.UserService;
import com.edu.domain.dto.UserLoginRequest;
import com.edu.entity.BasicResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;

    @PostMapping("/login")
    public BasicResponse login(UserLoginRequest userLoginRequest){

        userService.login(userLoginRequest);

        return BasicResponse.builder()
                .status(HttpStatus.OK.value())
                .build();
    }
}
