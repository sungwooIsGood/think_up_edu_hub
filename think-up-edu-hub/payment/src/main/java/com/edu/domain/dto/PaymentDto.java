package com.edu.domain.dto;

import com.edu.domain.entity.Payment;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
public class PaymentDto {
    private Long paymentId;
    private Long matchedLectureId;
    private Long lectureId;
    private Long userId;
    private LocalDateTime payDay;
    private BigDecimal price;

    @Builder
    public PaymentDto(Long paymentId, Long matchedLectureId, Long lectureId, Long userId, LocalDateTime payDay, BigDecimal price) {
        this.paymentId = paymentId;
        this.matchedLectureId = matchedLectureId;
        this.lectureId = lectureId;
        this.userId = userId;
        this.payDay = payDay;
        this.price = price;
    }

    public PaymentDto(Payment payment) {
        this.paymentId = payment.getPaymentId();
        this.matchedLectureId = payment.getMatchedLectureId();
        this.lectureId = payment.getLectureId();
        this.userId = payment.getUserId();
        this.payDay = payment.getPayDay();
        this.price = payment.getPrice();
    }
}
