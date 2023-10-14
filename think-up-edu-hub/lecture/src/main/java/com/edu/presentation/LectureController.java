package com.edu.presentation;

import com.edu.domain.dto.JwtVerifyResultItem;
import com.edu.domain.dto.LectureRegisterRequest;
import com.edu.domain.service.LectureService;
import com.edu.entity.BasicResponse;
import com.edu.infrastructure.aspect.JwtVerification;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/lecture")
public class LectureController {

    private final LectureService lectureService;

    /**
     * 수업 등록
     */
    @JwtVerification
    @PostMapping("")
    public BasicResponse post(LectureRegisterRequest lectureRegisterRequest, JwtVerifyResultItem jwtVerifyResultItem){
        return null;
    }
}
