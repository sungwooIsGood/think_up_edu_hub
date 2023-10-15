package com.edu.domain.entity;

import com.edu.domain.dto.LectureRegisterRequest;
import com.edu.domain.value.LectureDuration;
import com.edu.domain.value.LectureInfo;
import com.edu.domain.value.PriceInfo;
import com.edu.entity.BaseEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@Getter
public class Lecture extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long lectureId;
    private Long userId;

    @Embedded
    private LectureInfo lectureInfo;

    @Embedded
    private LectureDuration lectureDuration;

    @Embedded
    private PriceInfo priceInfo;

    @Builder
    public Lecture(Long lectureId, Long userId, LectureInfo lectureInfo, LectureDuration lectureDuration, PriceInfo priceInfo) {
        this.lectureId = lectureId;
        this.userId = userId;
        this.lectureInfo = lectureInfo;
        this.lectureDuration = lectureDuration;
        this.priceInfo = priceInfo;
    }

    public static Lecture createdLecture(LectureRegisterRequest lectureRegisterRequest, Long userId){
        return Lecture.builder()
                .userId(userId)
                .lectureInfo(createdLectureInfo(lectureRegisterRequest))
                .lectureDuration(createdDuration(lectureRegisterRequest))
                .priceInfo(createdPriceInfo(lectureRegisterRequest))
                .build();
    }

    private static LectureInfo createdLectureInfo(LectureRegisterRequest lectureRegisterRequest) {
        return LectureInfo.builder()
                .title(lectureRegisterRequest.getTitle())
                .description(lectureRegisterRequest.getDescription())
                .capacity(lectureRegisterRequest.getCapacity())
                .lectureState(lectureRegisterRequest.getLectureState())
                .build();
    }

    private static LectureDuration createdDuration(LectureRegisterRequest lectureRegisterRequest) {
        return LectureDuration.builder()
                .startDate(lectureRegisterRequest.getStartDate())
                .endDate(lectureRegisterRequest.getEndDate())
                .build();
    }

    private static PriceInfo createdPriceInfo(LectureRegisterRequest lectureRegisterRequest) {
        return PriceInfo.builder()
                .price(lectureRegisterRequest.getPrice())
                .isDiscount(lectureRegisterRequest.isDiscount())
                .discountRate(lectureRegisterRequest.getDiscountRate())
                .build();
    }
}
