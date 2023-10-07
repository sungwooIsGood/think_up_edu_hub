package com.edu.infrastructure.database.queryDsl;

import com.edu.domain.dto.UserLoginResponse;
import com.edu.domain.repository.UserRepository;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Objects;

import static com.edu.domain.entity.QUser.user;

@Repository
@RequiredArgsConstructor
public class UserQRepository implements UserRepository {

    private final JPAQueryFactory query;

    @Override
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
}
