package com.edu.application;

import com.edu.domain.dto.UserSignUpRequest;
import com.edu.domain.repository.UserRepository;
import com.edu.infrastructure.database.queryDsl.UserQRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class VerificationSignUpComponent {

    private final UserRepository userRepository;
    private final UserQRepository userQRepository;
    private final Logger log = LoggerFactory.getLogger(this.getClass().getSimpleName());

    public boolean verifyCanSignUp(UserSignUpRequest userSignUpRequest) {

        boolean isNotSameEmail = verityEmail(userSignUpRequest.getUsername());
        boolean passwordOk = verifyPassword(userSignUpRequest.getPassword());
        boolean isDetailNotNull = verifyIsNotNullCheck(userSignUpRequest);

        return isNotSameEmail && passwordOk && isDetailNotNull;
    }

    private boolean verityEmail(String username) {

        if(Objects.nonNull(username)){
            return userQRepository.findByUsername(username);
        }
        return false;
    }

    public boolean verifyPassword(String password) {
        String numberString = "0123456789";
        String specialCharacterString = "!@#$%^&*()_+-=[]{},./<>?~\\|;:\"`";

        int numberCount = 0;
        int englishCount = 0;
        int specialCharacterCount = 0;

        for (int i = 0; i < password.length(); i++) {
            if (numberString.indexOf(password.charAt(i)) != -1) {
                numberCount++;
            }

            if (specialCharacterString.indexOf(password.charAt(i)) != -1) {
                specialCharacterCount++;
            }

            if ((password.charAt(i) >= 'a' && password.charAt(i) <= 'z') || (password.charAt(i) >= 'A' && password.charAt(i) <= 'Z')) {
                englishCount++;
            }
        }

        if (specialCharacterCount >= 1 && englishCount >= 2 && numberCount >= 6) {
            log.info("영문/숫자/특수문자를 모두 입력하였음. 입력값: {}",password);
            return true;
        }

        log.info("영문/숫자/특수문자 중 하나를 충족하지 못함. 입력값: {}",password);
        throw new IllegalArgumentException("영문/숫자/특수문자 중 하나를 충족하지 못함. 입력값: " + password);
    }

    private boolean verifyIsNotNullCheck(UserSignUpRequest userSignUpRequest) {

        if(Objects.isNull(userSignUpRequest.getName())){
            throw new IllegalArgumentException("이름은 필수 입력값 입니다.");
        }

        if(Objects.isNull(userSignUpRequest.getBirthday())){
            throw new IllegalArgumentException("생일은 필수 입력값 입니다.");
        }

        if(Objects.isNull(userSignUpRequest.getGender())){
            throw new IllegalArgumentException("성별은 필수 입력값 입니다.");
        }

        if(Objects.isNull(userSignUpRequest.getPhoneNumber())){
            throw new IllegalArgumentException("핸드폰 번호는 필수 입력값 입니다.");
        }

        if(Objects.isNull(userSignUpRequest.getAddress())){
            throw new IllegalArgumentException("주소지 압력은 필수 입력값 입니다.");
        }

        if(Objects.isNull(userSignUpRequest.getUserType())){
            throw new IllegalArgumentException("학생인지 선생님인지 필수로 입력하셔야 합니다.");
        }

        return true;
    }
}
