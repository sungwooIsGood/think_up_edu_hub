package com.edu.domain.dto;

import com.edu.domain.entity.User;
import com.edu.domain.value.LoginInfo;
import com.edu.domain.value.UserInfo;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class UserLoginResponse {

    private Long id;
    private UserInfo userInfo;
    private LoginInfo loginInfo;

    @Builder
    public UserLoginResponse(User user) {
        this.id = user.getUserId();
        this.userInfo = user.getUserInfo();
        this.loginInfo = user.getLoginInfo();
    }
}
