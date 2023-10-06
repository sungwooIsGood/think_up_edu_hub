package com.edu.domain.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.edu.entity.BaseEntity;

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

    @Builder
    public MatchedLecture(Long matchedLectureId, Long lectureId, Long userId) {
        this.matchedLectureId = matchedLectureId;
        this.lectureId = lectureId;
        this.userId = userId;
    }
}
