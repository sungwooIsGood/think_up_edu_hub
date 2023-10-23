package com.edu.domain.matchedLecture.entity;

import com.edu.entity.BaseEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@Getter
public class MatchedLecture extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long matchedLectureId;
    private Long lectureId;
    private Long userId;
    private Boolean deletedCk;
    @ColumnDefault("0")
    private int sendEmailCount;

    @Builder
    public MatchedLecture(Long matchedLectureId, Long lectureId, Long userId,Boolean deletedCk, int sendEmailCount) {
        this.matchedLectureId = matchedLectureId;
        this.lectureId = lectureId;
        this.userId = userId;
        this.deletedCk = deletedCk;
        this.sendEmailCount = sendEmailCount;
    }

    public static MatchedLecture createdMatchedLecture(Long userId, Long lectureId){
        return MatchedLecture.builder()
                .lectureId(lectureId)
                .userId(userId)
                .deletedCk(false)
                .build();
    }

    public void softDelete(){
        this.deletedCk = true;
    }

    public void sendEmailCountUp(){
        sendEmailCount += 1;
    }

    // 깊은 복사용 팩토리
    public MatchedLecture copyMatchedLecture(MatchedLecture matchedLecture){
        return MatchedLecture.builder()
                .matchedLectureId(matchedLecture.getMatchedLectureId())
                .lectureId(matchedLecture.getLectureId())
                .userId(matchedLecture.getMatchedLectureId())
                .deletedCk(matchedLecture.getDeletedCk())
                .sendEmailCount(matchedLecture.getSendEmailCount())
                .build();
    }
}
