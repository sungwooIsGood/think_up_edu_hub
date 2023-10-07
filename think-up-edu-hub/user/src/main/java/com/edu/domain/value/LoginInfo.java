package com.edu.domain.value;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Embeddable;

@Embeddable
@NoArgsConstructor
@Getter
public class LoginInfo {

    private String username;
    private String password;

    @Builder
    public LoginInfo(String username, String password) {
        this.username = username;
        this.password = password;
    }
}
