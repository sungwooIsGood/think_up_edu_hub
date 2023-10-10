package com.edu.domain.service;

import com.edu.domain.dto.JwtPayload;
import com.edu.domain.dto.JwtTokenGroup;
import com.edu.domain.dto.UserLoginItem;

public interface JwtAuthService {

    JwtTokenGroup authenticateLogin(UserLoginItem userLoginItem);
    String getExpiredAccessToken(String authorizationToken);
    JwtPayload getExpiredAccessTokenByJwtDecode(String expiredAccessToken);

}
