package com.edu.domain.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class JwtTokenGroup {
    private String accessToken;
    private String refreshToken;

    @Builder
    public JwtTokenGroup(String accessToken, String refreshToken) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }

    public static JwtTokenGroup createdJwtTokenGroup(String accessToken,String refreshToken){
        return JwtTokenGroup.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }
}
