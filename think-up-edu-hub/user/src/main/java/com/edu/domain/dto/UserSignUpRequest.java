package com.edu.domain.dto;

import com.edu.domain.entity.User;
import com.edu.domain.enums.RoleType;
import com.edu.domain.enums.UserType;
import com.edu.domain.value.LoginInfo;
import com.edu.domain.value.UserInfo;
import lombok.Data;

@Data
public class UserSignUpRequest {

    private String name;
    private String birthday;
    private String gender;
    private String phoneNumber;
    private String address;
    private String username;
    private String password;
    private UserType userType;

    public User createdUser(String encodePassword){
        return User.builder()
                .userInfo(createdUserInfo())
                .loginInfo(createdLoginInfo(encodePassword))
                .build();
    }

    private UserInfo createdUserInfo() {
        return UserInfo.builder()
                .name(this.name)
                .birthday(this.birthday)
                .gender(this.gender)
                .phoneNumber(this.phoneNumber)
                .address(this.address)
                .userType(this.userType)
                .roleType(RoleType.USER)
                .build();
    }

    private LoginInfo createdLoginInfo(String encodePassword) {
        return LoginInfo.builder()
                .username(this.username)
                .password(encodePassword)
                .build();
    }
}
