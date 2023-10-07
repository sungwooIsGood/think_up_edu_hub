package com.edu.domain.value;

import com.edu.domain.enums.RoleType;
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
    private RoleType roleType;
}
