package com.edu.domain.repository;

import com.edu.domain.dto.LectureListResponse;
import com.edu.domain.entity.Lecture;
import com.edu.domain.enums.LectureType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface LectureJRepository extends JpaRepository<Lecture,Long> {

    @Query("select new com.edu.domain.dto.LectureListResponse(l,u)" +
            " from Lecture l" +
            " inner join User u" +
            " on l.userId = u.userId" +
            " where l.lectureInfo.lectureType = :lectureType" +
            " order by l.lectureId desc")
    Page<LectureListResponse> postedLectureListByLectureType(@Param("lectureType") LectureType lectureType, Pageable pageable);

    @Query("select new com.edu.domain.dto.LectureListResponse(l,u)" +
            " from Lecture l" +
            " inner join User u" +
            " on l.userId = u.userId" +
            " order by l.lectureId desc")
    Page<LectureListResponse> postedLectureList(Pageable pageable);
}
