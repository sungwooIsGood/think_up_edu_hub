package com.edu.domain.dto;

import com.edu.domain.entity.User;
import com.edu.domain.enums.TokenStateType;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class LoginVerifyItem {

    private Long userId;
    private String username;
    private String name;
    private String accessToken;
    private TokenStateType tokenStateType;

    @Builder
    public LoginVerifyItem(User user) {
        this.userId = user.getUserId();
        this.username = user.getLoginInfo().getUsername();
        this.name = user.getUserInfo().getName();
    }

    public void putAccessToken(String accessToken){
        this.accessToken = accessToken;
    }

    public void putTokenStateType(TokenStateType tokenStateType){
        this.tokenStateType = tokenStateType;
    }
}
