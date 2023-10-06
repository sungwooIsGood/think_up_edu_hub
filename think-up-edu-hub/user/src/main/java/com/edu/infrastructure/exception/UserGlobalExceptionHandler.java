package com.edu.infrastructure.exception;

import org.edu.enums.ErrorCode;
import org.edu.entity.BasicErrorResponse;
import org.edu.entity.BasicResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;

@RestControllerAdvice(basePackages = "com.edu")
public class UserGlobalExceptionHandler {

    private final Logger log = LoggerFactory.getLogger(this.getClass().getSimpleName());

    @ExceptionHandler(IllegalArgumentException.class)
    public BasicResponse illegalArgumentException(IllegalArgumentException e, HttpServletRequest request) {

        HashMap<String, Object> responseData = new HashMap<>();
        log.info("url: {}, message: {}", request.getRequestURI(), e.getMessage());

        responseData.put("message", e.getMessage());

        return BasicResponse.builder()
                .status(HttpStatus.BAD_REQUEST.value())
                .data(responseData)
                .error(new BasicErrorResponse(ErrorCode.INVALID_VALUE))
                .build();
    }
}
