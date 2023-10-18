package com.edu.application;

import com.edu.domain.entity.MatchedLecture;
import com.edu.domain.repository.LectureJRepository;
import com.edu.domain.repository.MatchedLectureJRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MatchedLectureService {

    private final LectureJRepository lectureJRepository;
    private final MatchedLectureJRepository matchedLectureJRepository;
    private final Logger log = LoggerFactory.getLogger(this.getClass().getSimpleName());

    public Long isExceedThNumberOfCapacity(Long userId, Long lectureId) {

        int capacity = lectureJRepository.findById(lectureId).orElseThrow(
                () -> new IllegalStateException("찾고자 하는 강의가 없습니다. lectureId: " + lectureId)
        ).getLectureInfo().getCapacity();

        Long matchedStudentCount = matchedLectureJRepository.countByLectureId(lectureId);
        log.info("현재 수강 신청 인원: {}",matchedStudentCount);

        if(capacity < matchedStudentCount + 1){
            throw new IllegalStateException("수강 신청 인원이 모두 꽉 찾습니다.");
        }

        return saveMatchedLecture(userId,lectureId);
    }

    private Long saveMatchedLecture(Long userId, Long lectureId) {
        MatchedLecture matchedLecture = MatchedLecture.createdMatchedLecture(userId, lectureId);
        return matchedLectureJRepository.save(matchedLecture).getLectureId();
    }
}
