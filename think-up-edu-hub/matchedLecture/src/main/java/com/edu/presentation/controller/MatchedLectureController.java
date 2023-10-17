package com.edu.presentation.controller;

import com.edu.application.MatchedLectureService;
import com.edu.domain.dto.JwtVerifyResultItem;
import com.edu.entity.BasicErrorResponse;
import com.edu.entity.BasicResponse;
import com.edu.enums.ErrorCode;
import com.edu.infrastructure.aspect.JwtVerification;
import com.edu.infrastructure.aspect.redis.DistributedLock;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/matched")
public class MatchedLectureController {

    private final MatchedLectureService matchedLectureService;

    @JwtVerification
    @PostMapping("/{lectureId}")
    public BasicResponse registerLecture(@PathVariable(name = "lectureId") Long lectureId,
                                         JwtVerifyResultItem jwtVerifyResultItem){

        if(jwtVerifyResultItem.isLogin()){

            Long lectureIdAfterSave = matchedLectureService.registerLecture(jwtVerifyResultItem.getUserId(), lectureId);

            Map<String,Object> responseData = new HashMap<>();
            responseData.put("lecturedId",lectureIdAfterSave);

            return BasicResponse.builder()
                    .status(HttpStatus.OK.value())
                    .data(responseData)
                    .build();

        } else{
            return BasicResponse.builder()
                    .status(HttpStatus.BAD_REQUEST.value())
                    .error(new BasicErrorResponse(ErrorCode.IS_NOT_LOGIN))
                    .build();
        }
    }
}
