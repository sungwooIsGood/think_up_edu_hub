package com.edu.domain.repository;

import com.edu.domain.entity.MatchedLecture;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MatchedLectureJRepository extends JpaRepository<MatchedLecture,Long> {
    Long countByLectureId(Long lectureId);
}
