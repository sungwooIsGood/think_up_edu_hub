package com.edu.application;

import com.edu.domain.dto.LectureRegisterRequest;
import com.edu.domain.entity.Lecture;
import com.edu.domain.entity.User;
import com.edu.domain.repository.LectureRepository;
import com.edu.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class LectureService {

    private final LectureRepository lectureRepository;
    private final UserRepository userRepository;
    private final Logger log = LoggerFactory.getLogger(this.getClass().getSimpleName());

    @Transactional
    public void post(LectureRegisterRequest lectureRegisterRequest, Long userId) {

        try{
            User user = userRepository.findByIdAboutTeacher(userId);
            Lecture lecture = Lecture.createdLecture(lectureRegisterRequest, user.getUserId());
            lectureRepository.save(lecture);
        } catch (Exception e){
            log.error("예상치 못한 에러가 발생했습니다. 입력값: {}", lectureRegisterRequest);
            throw new IllegalStateException("예상치 못한 에러가 발생했습니다. 입력값: " + lectureRegisterRequest);
        }


    }
}
