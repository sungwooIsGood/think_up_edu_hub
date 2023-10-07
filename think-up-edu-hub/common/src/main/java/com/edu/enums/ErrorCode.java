package com.edu.enums;

import lombok.Getter;

@Getter
public enum ErrorCode {

    INVALID_VALUE("1","잘못된 인수가 전달되었습니다.");

    private final String errorCode;
    private final String message;

    ErrorCode(String errorCode, String message) {
        this.errorCode = errorCode;
        this.message = message;
    }
}
