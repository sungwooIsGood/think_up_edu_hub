package com.edu.domain.user.value;

import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;

@Embeddable
@NoArgsConstructor
public class UserInfo {

    private String name;
    private String birthday;
    private String gender;
    private String phoneNumber;
    private String address;
}
