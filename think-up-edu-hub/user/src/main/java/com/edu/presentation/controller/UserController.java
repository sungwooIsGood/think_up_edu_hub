package com.edu.presentation.controller;

import com.edu.entity.BasicResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserController {


    @PostMapping("/login")
    public BasicResponse login(){
        throw new IllegalArgumentException("에러발생");
    }
}
