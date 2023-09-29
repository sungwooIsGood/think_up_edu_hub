package com.edu.lecture.value;

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
    private BigDecimal price;

    private int capacity;

    @Enumerated(value = EnumType.STRING)
    private LectureState lectureState;

}
