package org.edu.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.edu.enums.ErrorCode;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class BasicErrorResponse {

    private String errorCode; //에러 코드
    private String message; // 에러 메시지

    public BasicErrorResponse(ErrorCode errorCode) {
        this.errorCode = errorCode.getErrorCode();
        this.message = errorCode.getMessage();
    }

}
