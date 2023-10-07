package com.edu.infrastructure.database.queryDsl;

import com.edu.domain.dto.UserLoginResponse;
import com.edu.domain.entity.QUser;
import com.edu.domain.entity.User;
import com.edu.domain.repository.UserRepository;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Objects;
import java.util.Optional;

import static com.edu.domain.entity.QUser.user;

@Repository
@RequiredArgsConstructor
public class UserQRepository {

    private final JPAQueryFactory query;

    public UserLoginResponse findByUsernameAndPassword(String username, String password){

        UserLoginResponse userLoginResponse = query.select(Projections.constructor(UserLoginResponse.class, user))
                .from(user)
                .where(user.loginInfo.username.eq(username)
                        .and(user.loginInfo.password.eq(password))
                )
                .fetchOne();

        checkUsernameAndPwdIsTrue(userLoginResponse);

        return userLoginResponse;
    }

    private void checkUsernameAndPwdIsTrue(UserLoginResponse userLoginResponse) {

        if(Objects.isNull(userLoginResponse)){
            throw new IllegalArgumentException("아이디 혹은 비밀번호가 틀렸습니다.");
        }
    }

    public boolean findByUsername(String username) {

        User user = query.select(QUser.user)
                .from(QUser.user)
                .where(QUser.user.loginInfo.username.eq(username))
                .fetchOne();

        if(Objects.nonNull(user)){
            throw new IllegalArgumentException("똑같은 이메일이 존재합니다.");
        }
        return true;
    }
}
