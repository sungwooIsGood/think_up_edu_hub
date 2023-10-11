package com.edu.domain.service;

import com.edu.domain.dto.JwtPayload;
import com.edu.domain.dto.JwtTokenGroup;

public interface JwtAuthService {

    JwtTokenGroup createAccAndRefreshToken(Long userId);
    String getAccessTokenByHeader(String authorizationToken);
    JwtPayload getPayloadByJwtDecode(String accessToken);
    boolean validateTokenExpiredDate(String authorizationToken);
}
