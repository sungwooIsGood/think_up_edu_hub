package com.edu.domain.repository;

import com.edu.domain.dto.LoginVerifyItem;
import com.edu.domain.dto.UserLoginItem;
import com.edu.domain.entity.User;

public interface UserRepository {

    UserLoginItem findByUsernameAndPassword(String username, String password);
    boolean findByUsername(String username);
    LoginVerifyItem findById(Long userId);
    User findByIdAboutTeacher(Long userId);
}
