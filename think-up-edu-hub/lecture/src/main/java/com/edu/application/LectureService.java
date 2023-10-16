package com.edu.application;

import com.edu.domain.dto.LectureListResponse;
import com.edu.domain.dto.LectureRegisterRequest;
import com.edu.domain.entity.Lecture;
import com.edu.domain.entity.User;
import com.edu.domain.enums.LectureType;
import com.edu.domain.repository.LectureRepository;
import com.edu.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class LectureService {

    private final LectureRepository lectureRepository;
    private final UserRepository userRepository;
    private final Logger log = LoggerFactory.getLogger(this.getClass().getSimpleName());

    @Transactional
    public void post(LectureRegisterRequest lectureRegisterRequest, Long userId) {

        User user = userRepository.findByIdAboutTeacher(userId);
        Lecture lecture = Lecture.createdLecture(lectureRegisterRequest, user.getUserId());
        lectureRepository.save(lecture);

    }

    @Transactional(readOnly = true)
    public Page<LectureListResponse> postedLectureList(LectureType lectureType, Pageable pageable) {

        try{
            if(Objects.nonNull(lectureType)){
                return lectureRepository.postedLectureListByLectureType(lectureType,pageable);
            } else{
                return lectureRepository.postedLectureList(pageable);
            }
        } catch (Exception e){
            log.error("예상치 못한 에러가 발생했습니다. 입력 페이지: {}", pageable.getOffset());
            throw new IllegalStateException("예상치 못한 에러가 발생했습니다. 입력 페이지: " + pageable.getOffset());
        }

    }
}
