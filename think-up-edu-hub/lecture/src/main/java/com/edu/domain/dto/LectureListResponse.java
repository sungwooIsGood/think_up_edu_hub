package com.edu.domain.dto;

import com.edu.domain.entity.Lecture;
import com.edu.domain.entity.User;
import com.edu.domain.enums.LectureType;
import com.edu.domain.value.LectureDuration;
import com.edu.domain.value.LectureInfo;
import com.edu.domain.value.LectureState;
import com.edu.domain.value.PriceInfo;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class LectureListResponse {

    private String title;
    private String userName;
    private Long userId;
    private Long lectureId;

    private String description;

    private int capacity;

    private LectureState lectureState = LectureState.ACTIVE;

    private LocalDateTime startDate;

    private LocalDateTime endDate;

    private BigDecimal price;

    private boolean isDiscount;

    private Double discountRate;

    private LectureType lectureType;

    @Builder
    public LectureListResponse(String title, String userName, Long userId, Long lectureId,
                               String description, int capacity, LectureState lectureState,
                               LocalDateTime startDate, LocalDateTime endDate,
                               BigDecimal price, boolean isDiscount, Double discountRate, LectureType lectureType) {
        this.title = title;
        this.userName = userName;
        this.userId = userId;
        this.lectureId = lectureId;
        this.description = description;
        this.capacity = capacity;
        this.lectureState = lectureState;
        this.startDate = startDate;
        this.endDate = endDate;
        this.price = price;
        this.isDiscount = isDiscount;
        this.discountRate = discountRate;
        this.lectureType = lectureType;
    }

    @Builder
    public LectureListResponse(Lecture lecture, User user) {
        this.title = lecture.getLectureInfo().getTitle();
        this.userName = user.getUserInfo().getName();
        this.userId = user.getUserId();
        this.lectureId = lecture.getLectureId();
        this.description = lecture.getLectureInfo().getDescription();
        this.capacity = lecture.getLectureInfo().getCapacity();
        this.lectureState = lecture.getLectureInfo().getLectureState();
        this.startDate = lecture.getLectureDuration().getStartDate();
        this.endDate = lecture.getLectureDuration().getEndDate();
        this.price = lecture.getPriceInfo().getPrice();
        this.isDiscount = lecture.getPriceInfo().isDiscount();
        this.discountRate = lecture.getPriceInfo().getDiscountRate();
        this.lectureType = lecture.getLectureInfo().getLectureType();
    }

}
