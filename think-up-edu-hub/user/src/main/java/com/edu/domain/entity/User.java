package com.edu.domain.entity;

import com.edu.domain.value.LoginInfo;
import com.edu.domain.value.UserInfo;
import com.edu.entity.BaseEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@Getter
public class User extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @Embedded
    private UserInfo userInfo;

    @Embedded
    private LoginInfo loginInfo;

    private boolean deleteCk;

    @Builder
    public User(Long userId, UserInfo userInfo, LoginInfo loginInfo) {
        this.userId = userId;
        this.userInfo = userInfo;
        this.loginInfo = loginInfo;
    }

    public boolean isSamePassword(String password){

        if(loginInfo.getPassword().equals(password)){
            this.userInfo = null;
            this.loginInfo = null;
            this.deleteCk = true;
            return true;
        } else{
            this.deleteCk = false;
            return false;
        }

    }
}
