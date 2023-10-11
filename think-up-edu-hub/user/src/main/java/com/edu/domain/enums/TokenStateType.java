package com.edu.domain.enums;

import lombok.Getter;

@Getter
public enum TokenStateType {

    TOKEN_VALID("TOKEN_VALID", "토큰 유효"),
    TOKEN_RESET("TOKEN_RESET","토큰 재발급"),
    TOKEN_EXPIRE("TOKEN_EXPIRE", "토큰 만료"),
    TOKEN_FORM_NOT_VALID("TOKEN_FORM_NOT_VALID", "토큰 형식 문제"),
    TOKEN_IS_NOT_VALID("TOKEN_IS_NOT_VALID","토큰의 값이 존재하지 않음");

    private final String tokenState;
    private final String explanation;


    TokenStateType(String tokenState, String explanation) {
        this.tokenState = tokenState;
        this.explanation = explanation;
    }
}
