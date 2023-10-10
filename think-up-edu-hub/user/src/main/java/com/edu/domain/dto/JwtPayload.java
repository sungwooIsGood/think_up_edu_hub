package com.edu.domain.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class JwtPayload {

    @JsonProperty("iat")
    private int iat;

    @JsonProperty("exp")
    private int exp;
    private String userId;
    private String tokenIssuer;

    @Builder
    public JwtPayload(int tokenCreatedAt, int tokenExpireAt,
                      String userId, String tokenIssuer) {
        this.iat = tokenCreatedAt;
        this.exp = tokenExpireAt;
        this.userId = userId;
        this.tokenIssuer = tokenIssuer;
    }
}
