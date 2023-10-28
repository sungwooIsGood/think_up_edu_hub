package com.edu.domain.payment.entity;

import com.edu.component.CommonComponent;
import com.edu.domain.payment.dto.PaymentResponse;
import com.edu.domain.payment.enums.PaymentMethod;
import com.edu.domain.payment.enums.PaymentStatus;
import com.edu.entity.BaseEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@Getter
public class Payment extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long paymentId;
    private Long matchedLectureId;
    private Long lectureId;
    private Long userId;
    @Column(columnDefinition = "timestamp")
    private LocalDateTime payDay;
    private BigDecimal price;
    @Enumerated(EnumType.STRING)
    private PaymentMethod payMethod;
    @Enumerated(EnumType.STRING)
    private PaymentStatus paymentStatus;
    private String impUid; // 고유 결제번호
    private String merchantUid; // 주문번호

    @Builder
    public Payment(Long paymentId, Long matchedLectureId, Long lectureId, Long userId, LocalDateTime payDay,
                   BigDecimal price, PaymentMethod payMethod, PaymentStatus paymentStatus, String impUid, String merchantUid) {
        this.paymentId = paymentId;
        this.matchedLectureId = matchedLectureId;
        this.lectureId = lectureId;
        this.userId = userId;
        this.payDay = payDay;
        this.price = price;
        this.payMethod = payMethod;
        this.paymentStatus = paymentStatus;
        this.impUid = impUid;
        this.merchantUid = merchantUid;
    }

//    public static Payment createdPayment(Long userId,Long matchedLectureId,Long lectureId,PaymentResponse paymentResponse){
//        return Payment.builder()
//                .matchedLectureId(matchedLectureId)
//                .lectureId(lectureId)
//                .userId(userId)
//                .payDay(CommonComponent.convertTimestampToLocalDateTime(paymentResponse.getPaidAt()))
//                .price()
//                .payMethod()
//                .paymentStatus()
//                .impUid()
//                .merchantUid()
//                .build();
//    }
}
