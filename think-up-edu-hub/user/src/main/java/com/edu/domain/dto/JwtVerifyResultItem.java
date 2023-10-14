package com.edu.domain.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Getter
@NoArgsConstructor
public class JwtVerifyResultItem {

    private Long userId;
    private boolean isLogin; // 로근인 여부
    private boolean accessTokenResetResult; // access token 만료 되어 재발급 되었으면 ture / 프론트에서 다시 cookie 저장하게 알려주어야 한다.

    @Builder
    public JwtVerifyResultItem(Long userId, boolean isLogin, boolean accessTokenResetResult) {
        this.userId = userId;
        this.isLogin = isLogin;
        this.accessTokenResetResult = accessTokenResetResult;
    }

    public static JwtVerifyResultItem createJwtVerifyResultItemByNewAcc(JwtPayload payload, String newAccessToken) {

        if(Objects.nonNull(newAccessToken)){
            return JwtVerifyResultItem.builder()
                    .userId(payload.getUserId())
                    .isLogin(true)
                    .accessTokenResetResult(true)
                    .build();
        } else{
            return JwtVerifyResultItem.builder()
                    .userId(null)
                    .isLogin(false)
                    .accessTokenResetResult(false)
                    .build();
        }
    }

    public static JwtVerifyResultItem createJwtVerifyResultItemByValid(JwtPayload payload, String newAccessToken) {

        if (Objects.isNull(newAccessToken)) {
            return JwtVerifyResultItem.builder()
                    .userId(payload.getUserId())
                    .isLogin(true)
                    .accessTokenResetResult(false)
                    .build();
        } else{
            return JwtVerifyResultItem.builder()
                    .userId(null)
                    .isLogin(false)
                    .accessTokenResetResult(false)
                    .build();
        }
    }
}
