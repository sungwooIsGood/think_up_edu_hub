package com.edu.domain.value;

import com.edu.domain.enums.RoleType;
import com.edu.domain.enums.UserType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Embeddable
@NoArgsConstructor
@Getter
public class UserInfo {

    private String name;
    private String birthday;
    private String gender;
    private String phoneNumber;
    private String address;
    @Enumerated(EnumType.STRING)
    private RoleType roleType;

    @Enumerated(EnumType.STRING)
    private UserType userType;

    @Builder
    public UserInfo(String name, String birthday, String gender,
                    String phoneNumber, String address, RoleType roleType,
                    UserType userType) {
        this.name = name;
        this.birthday = birthday;
        this.gender = gender;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.roleType = roleType;
        this.userType = userType;
    }
}
