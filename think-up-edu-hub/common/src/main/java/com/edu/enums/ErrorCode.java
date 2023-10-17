package com.edu.enums;

import lombok.Getter;

@Getter
public enum ErrorCode {

    INVALID_VALUE("1","잘못된 인수가 전달되었습니다."),
    IS_NOT_LOGIN("2","로그인된 사용자가 아닙니다."),
    NULL_VALUE("3","필수로 입력되어야 하는 값에 null이 입력되었습니다."),
    INVALID_UNLOCK_OPERATION("4","락이 반납하면서 에러가 발생했습니다."),
    LOCK_NOT_AVAILABLE("5","락을 얻는데 실패하였습니다."),
    INTERRUPTED_EXCEPTION("6","멀티 스레드 환경에서 해당 스레드가 어떤 작업 중에 인터럽트(Interrupt)를 받았습니다.");

    private final String errorCode;
    private final String message;

    ErrorCode(String errorCode, String message) {
        this.errorCode = errorCode;
        this.message = message;
    }
}
