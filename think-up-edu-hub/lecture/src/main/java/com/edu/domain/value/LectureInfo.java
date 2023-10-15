package com.edu.domain.value;

import com.edu.domain.enums.LectureType;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.math.BigDecimal;

@Embeddable
@NoArgsConstructor
@Getter
public class LectureInfo {

    private String title;
    private String description;

    private int capacity; // 강의 수용수
    private LectureType lectureType; // 과외 종류


    @Enumerated(value = EnumType.STRING)
    private LectureState lectureState;

    @Builder
    public LectureInfo(String title, String description, int capacity, LectureState lectureState,LectureType lectureType) {
        this.title = title;
        this.description = description;
        this.capacity = capacity;
        this.lectureState = lectureState;
        this.lectureType = lectureType;
    }
}
