package com.edu.infrastructure.database.queryDsl;

import com.edu.domain.dto.LoginVerifyItem;
import com.edu.domain.dto.UserLoginItem;
import com.edu.domain.entity.QUser;
import com.edu.domain.entity.User;
import com.edu.domain.enums.UserType;
import com.edu.domain.repository.UserRepository;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

import java.util.Objects;
import java.util.Optional;

import static com.edu.domain.entity.QUser.user;

@Repository
@RequiredArgsConstructor
public class UserQRepository implements UserRepository {

    private final JPAQueryFactory query;
    private final Logger log = LoggerFactory.getLogger(this.getClass().getSimpleName());

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

    @Override
    public LoginVerifyItem findById(Long userId) {
        LoginVerifyItem loginVerifyItem = query.select(Projections.constructor(LoginVerifyItem.class, user))
                .from(user)
                .where(user.userId.eq(userId))
                .fetchOne();

        if(Objects.isNull(loginVerifyItem)){
            log.info("들어온 userId: {}",userId);
            throw new IllegalStateException("찾고자 하는 유저의 정보가 없습니다.");
        }

        return loginVerifyItem;
    }

    @Override
    public User findByIdAboutTeacher(Long userId) {
        return Optional.ofNullable(query.select(user)
                .from(user)
                .where(user.userId.eq(userId)
                        .and(user.userInfo.userType.eq(UserType.TEACHER))
                )
                .fetchOne()).orElseThrow(() -> new IllegalStateException("선생님만이 과목을 등록할 수 있습니다."));
    }
}
