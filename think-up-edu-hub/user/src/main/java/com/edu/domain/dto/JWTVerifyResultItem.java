package com.edu.domain.dto;

import com.edu.domain.enums.TokenStateType;
import lombok.Getter;

import java.util.UUID;

@Getter
public class JWTVerifyResultItem {

    private Long userId;
    private boolean verificationResult; // 검증 완료 여부 -> 즉, 로근인 여부
    private boolean accessTokenResetResult; // access token 만료 되어 재발급 되었으면 ture / 프론트에서 다시 cookie 저장하게 알려주어야 한다.

    
}
