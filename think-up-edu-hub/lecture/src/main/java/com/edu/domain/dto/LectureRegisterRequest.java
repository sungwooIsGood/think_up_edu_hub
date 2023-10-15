package com.edu.domain.dto;

import com.edu.domain.value.LectureState;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
public class LectureRegisterRequest {

    private String title;
    private String description;
    private int capacity;
    private LectureState lectureState = LectureState.ACTIVE;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private BigDecimal price;
    private boolean isDiscount;
    private Double discountRate;
}
