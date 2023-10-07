package com.edu.domain.dto;

import com.edu.domain.entity.User;
import com.edu.domain.value.UserInfo;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class UserLoginResponse {

    private Long id;
    private UserInfo userInfo;

    @Builder
    public UserLoginResponse(User user) {
        this.id = user.getUserId();
        this.userInfo = user.getUserInfo();
    }
}
