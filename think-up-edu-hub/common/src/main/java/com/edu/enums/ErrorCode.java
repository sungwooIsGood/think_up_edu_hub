package com.edu.enums;

import lombok.Getter;

@Getter
public enum ErrorCode {

    INVALID_VALUE("1","잘못된 인수가 전달되었습니다."),
    IS_NOT_LOGIN("2","로그인된 사용자가 아닙니다."),
    NULL_VALUE("3","필수로 입력되어야 하는 값에 null이 입력되었습니다.");

    private final String errorCode;
    private final String message;

    ErrorCode(String errorCode, String message) {
        this.errorCode = errorCode;
        this.message = message;
    }
}
