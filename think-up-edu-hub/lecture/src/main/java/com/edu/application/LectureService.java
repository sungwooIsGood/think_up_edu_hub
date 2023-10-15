package com.edu.application;

import com.edu.domain.dto.LectureRegisterRequest;
import com.edu.domain.entity.Lecture;
import com.edu.domain.entity.User;
import com.edu.domain.repository.LectureRepository;
import com.edu.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class LectureService {

    private final LectureRepository lectureRepository;
    private final UserRepository userRepository;

    @Transactional
    public void post(LectureRegisterRequest lectureRegisterRequest, Long userId) {

        User user = userRepository.findByIdAboutTeacher(userId);


    }
}
