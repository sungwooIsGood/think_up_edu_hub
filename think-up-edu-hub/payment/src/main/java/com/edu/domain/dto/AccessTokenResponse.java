package com.edu.domain.dto;

import com.google.gson.annotations.SerializedName;
import lombok.Getter;

@Getter
public class AccessTokenResponse {

    private int code;
    private String message;
    private ResponseData response;

    @Getter
    public static class ResponseData {

        @SerializedName("access_token") // @JsonProperty("access_token") jackson 라이브러리 전용,
        private String accessToken;

        private long now;

        @SerializedName("expired_at")
        private long expiredAt;
    }
}
