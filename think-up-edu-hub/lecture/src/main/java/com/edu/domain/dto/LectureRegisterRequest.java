package com.edu.domain.dto;

import com.edu.domain.enums.LectureType;
import com.edu.domain.value.LectureState;
import lombok.*;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@NoArgsConstructor
@Data
public class LectureRegisterRequest {

    @NotNull(message = "생성할 과외 제목의 이름을 꼭 적어주세요.")
    private String title;

    @NotNull(message = "생성할 과외의 설명을 꼭 적어주세요.")
    private String description;

    @NotNull(message = "과외의 수용인원을 적어주세요.")
    private int capacity;
    private LectureState lectureState = LectureState.ACTIVE;

    @NotNull(message = "과외 시작시간을 적어주세요.")
    private LocalDateTime startDate;

    @NotNull(message = "과외 종료시간을 적어주세요.")
    private LocalDateTime endDate;

    @NotNull(message = "과외의 가격을 적어주세요.")
    private BigDecimal price;

    @NotNull(message = "할인 여부를 적어주세요.")
    private boolean isDiscount;

    @NotNull(message = "과외를 할인 하신다면 할인율을 적어주세요.")
    private Double discountRate;

    @NotNull(message = "과외 종류를 적어주세요.")
    private LectureType lectureType;
}
