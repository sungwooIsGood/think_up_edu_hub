package com.edu.domain.matchedLecture.value;

import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;

@Embeddable
@NoArgsConstructor
public class LoginInfo {

    private String username;
    private String password;
}
