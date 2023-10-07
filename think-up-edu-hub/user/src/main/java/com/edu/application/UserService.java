package com.edu.application;

import com.edu.domain.dto.UserLoginRequest;
import com.edu.domain.dto.UserLoginResponse;
import com.edu.domain.dto.UserSignUpRequest;
import com.edu.domain.entity.User;
import com.edu.infrastructure.database.jpa.UserJRepository;
import com.edu.infrastructure.database.queryDsl.UserQRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserJRepository userJRepository;
    private final UserQRepository userQRepository;
    private final VerificationSignUpComponent verificationSignUpComponent;
    private final PasswordEncoder passwordEncoder;
    @Transactional
    public UserLoginResponse login(UserLoginRequest userLoginRequest){

        // check login verification
        checkLoginRequestIsNull(userLoginRequest.getUsername(),userLoginRequest.getPassword());
        return userQRepository.findByUsernameAndPassword(userLoginRequest.getUsername(),userLoginRequest.getPassword());
    }

    private void checkLoginRequestIsNull(String username, String password) {

        if(Objects.isNull(username)){
            throw new IllegalArgumentException("username 값이 비어있습니다.");
        }

        if(Objects.isNull(password)){
            throw new IllegalArgumentException("password 값이 비어있습니다.");
        }

    }

    @Transactional
    public boolean signUp(UserSignUpRequest userSignUpRequest) {
        boolean isSignUpOk = verificationSignUpComponent.verifyCanSignUp(userSignUpRequest);

        // 회원가입 로직
        if(isSignUpOk){
            String encodePassword = passwordEncoder.encode(userSignUpRequest.getPassword()); // 비밀번호 인코딩
            User user = userSignUpRequest.createdUser(encodePassword);
            userJRepository.save(user);
        }

        throw new IllegalArgumentException("잘못된 값이 넘어왔습니다. 입력값: " + userSignUpRequest);
    }
}
