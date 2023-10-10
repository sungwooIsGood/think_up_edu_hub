package com.edu.infrastructure.database.queryDsl;

import com.edu.domain.dto.UserLoginItem;
import com.edu.domain.entity.QUser;
import com.edu.domain.entity.User;
import com.edu.domain.repository.UserRepository;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

import java.util.Objects;

import static com.edu.domain.entity.QUser.user;

@Repository
@RequiredArgsConstructor
public class UserQRepository implements UserRepository {

    private final JPAQueryFactory query;

    @Override
    public UserLoginItem findByUsernameAndPassword(String username, String password){

        UserLoginItem userLoginItem = query.select(Projections.constructor(UserLoginItem.class, user))
                .from(user)
                .where(user.loginInfo.username.eq(username))
                .fetchOne();

        checkUsernameAndPwdIsTrue(userLoginItem,password);

        return userLoginItem;
    }

    private void checkUsernameAndPwdIsTrue(UserLoginItem userLoginItem, String password) {

        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        if(Objects.isNull(userLoginItem) || !passwordEncoder.matches(password,userLoginItem.getLoginInfo().getPassword())){
            throw new IllegalArgumentException("아이디 혹은 비밀번호가 틀렸습니다.");
        }
    }

    @Override
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
