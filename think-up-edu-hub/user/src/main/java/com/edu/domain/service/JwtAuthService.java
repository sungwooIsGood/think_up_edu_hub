package com.edu.domain.service;

import com.edu.domain.dto.UserLoginItem;

public interface JwtAuthService {

    String authenticateLogin(UserLoginItem userLoginItem);
}
