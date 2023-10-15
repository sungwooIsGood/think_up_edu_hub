package com.edu.presentation;

import com.edu.domain.dto.JwtVerifyResultItem;
import com.edu.domain.dto.LectureRegisterRequest;
import com.edu.application.LectureService;
import com.edu.entity.BasicErrorResponse;
import com.edu.entity.BasicResponse;
import com.edu.enums.ErrorCode;
import com.edu.infrastructure.aspect.JwtVerification;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

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
    public BasicResponse post(@Valid LectureRegisterRequest lectureRegisterRequest, JwtVerifyResultItem jwtVerifyResultItem){

        if(jwtVerifyResultItem.isLogin()){
            lectureService.post(lectureRegisterRequest,jwtVerifyResultItem.getUserId());

            return BasicResponse.builder()
                    .status(HttpStatus.OK.value())
                    .build();

        } else{
            return BasicResponse.builder()
                    .status(HttpStatus.BAD_REQUEST.value())
                    .error(new BasicErrorResponse(ErrorCode.IS_NOT_LOGIN))
                    .build();
        }
    }
}
