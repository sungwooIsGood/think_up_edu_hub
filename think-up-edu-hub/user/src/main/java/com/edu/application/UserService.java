package com.edu.application;

import com.edu.domain.dto.UserLoginRequest;
import com.edu.domain.dto.UserLoginResponse;
import com.edu.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    @Transactional
    public UserLoginResponse login(UserLoginRequest userLoginRequest){

        // check login verification
        checkLoginRequestIsNull(userLoginRequest.getUsername(),userLoginRequest.getPassword());
        return userRepository.findByUsernameAndPassword(userLoginRequest.getUsername(),userLoginRequest.getPassword());
    }

    private void checkLoginRequestIsNull(String username, String password) {

        if(Objects.isNull(username)){
            throw new IllegalArgumentException("username 값이 비어있습니다.");
        }

        if(Objects.isNull(password)){
            throw new IllegalArgumentException("password 값이 비어있습니다.");
        }

    }
}
