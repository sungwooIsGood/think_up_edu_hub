package com.edu.domain.matchedLecture.repository;

import com.edu.domain.matchedLecture.dto.MatchedLectureDto;
import com.edu.domain.matchedLecture.entity.MatchedLecture;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MatchedLectureJRepository extends JpaRepository<MatchedLecture,Long> {
    Long countByLectureId(Long lectureId);

    @Query("select m" +
            " from MatchedLecture m" +
            " inner join Lecture l" +
            " on m.lectureId = l.lectureId" +
            " where l.lectureDuration.startDate < now()" +
            " and m.deletedCk = false")
    List<MatchedLecture> findAllLectureJoinWithStartTimeBeforeNow();
}
