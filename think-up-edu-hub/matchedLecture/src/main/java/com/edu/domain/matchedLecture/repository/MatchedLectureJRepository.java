package com.edu.domain.matchedLecture.repository;

import com.edu.domain.matchedLecture.dto.EmailDetailItem;
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
            " where l.lectureDuration.startDate > now()" +
            " and m.deletedCk = false")
    List<MatchedLecture> findAllLectureJoinWithStartTimeBeforeNow();

    @Query("select new com.edu.domain.matchedLecture.dto.EmailDetailItem(m,u,l)" +
            " from MatchedLecture m" +
            " inner join User u" +
            " on m.userId = u.userId" +
            " inner join Lecture l" +
            " on m.lectureId = l.lectureId" +
            " where m.userId = :userId")
    EmailDetailItem getEmailDetailItem(Long userId);

    @Modifying
    @Query("update MatchedLecture m" +
            " set m.sendEmailCount = m.sendEmailCount + 1" +
            " where m.userId = :userId")
    void updateSendEmailCountPlus(Long userId);
}
