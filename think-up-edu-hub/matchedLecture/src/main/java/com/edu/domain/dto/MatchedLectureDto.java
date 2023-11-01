package com.edu.domain.dto;

import com.edu.domain.entity.MatchedLecture;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class MatchedLectureDto {

    private Long matchedLectureId;
    private Long lectureId;
    private Long userId;
    private LocalDateTime createdAt;
    private int sendEmailCount;

    @Builder
    public MatchedLectureDto(Long matchedLectureId, Long lectureId, Long userId,
                             LocalDateTime createdAt, int sendEmailCount) {
        this.matchedLectureId = matchedLectureId;
        this.lectureId = lectureId;
        this.userId = userId;
        this.createdAt = createdAt;
        this.sendEmailCount = sendEmailCount;
    }

    public MatchedLectureDto(MatchedLecture matchedLecture) {
        this.matchedLectureId = matchedLecture.getMatchedLectureId();
        this.lectureId = matchedLecture.getLectureId();
        this.userId = matchedLecture.getUserId();
        this.createdAt = matchedLecture.getCreatedAt();
        this.sendEmailCount = matchedLecture.getSendEmailCount();
    }
}
