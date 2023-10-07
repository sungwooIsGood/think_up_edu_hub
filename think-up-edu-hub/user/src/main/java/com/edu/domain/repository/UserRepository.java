package com.edu.domain.repository;

import com.edu.domain.dto.UserLoginResponse;

public interface UserRepository {

    UserLoginResponse findByUsernameAndPassword(String username, String password);
}
