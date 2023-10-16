package com.edu.presentation;

import com.edu.domain.dto.JwtVerifyResultItem;
import com.edu.domain.dto.LectureRegisterRequest;
import com.edu.application.LectureService;
import com.edu.domain.enums.LectureType;
import com.edu.entity.BasicErrorResponse;
import com.edu.entity.BasicResponse;
import com.edu.enums.ErrorCode;
import com.edu.infrastructure.aspect.JwtVerification;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

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

    /**
     * 과외 리스트 보기 페이징
     */
    @GetMapping(value = {"pagination/{lectureType}", "pagination"})
    public BasicResponse postedLectureList(@PathVariable(required = false, value = "lectureType") LectureType lectureType,
                                           @PageableDefault(size=10) Pageable pageable){

        Map<String,Object> responseData = new HashMap<>();
        responseData.put("lecturePagination",lectureService.postedLectureList(lectureType,pageable));

        return BasicResponse.builder()
                .status(HttpStatus.OK.value())
                .data(responseData)
                .build();
    }
}
