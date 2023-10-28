package com.edu.domain.payment.dto;

import com.edu.domain.payment.entity.Payment;
import com.edu.domain.payment.enums.PaymentMethod;
import com.edu.domain.payment.enums.PaymentStatus;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.math.BigDecimal;

@Getter
public class PaymentVerificationRequest {

    private Long userId; // 유저 ID
    private Long lectureId; // 강의 ID
    private Long matchedLectureId; // 수강신청 ID
    private boolean success; // 결제 성공여부
    private String errorCode; // 결제 실패코드
    private String errorMessage; // 결제 실패 메세지
    private String impUid; // 고유 결제번호
    private String merchantUid; // 주문번호
    private PaymentMethod payMethod; // 결제수단 구분코드
    private BigDecimal paidAmount; // 결제금액
    private PaymentStatus status; // 결제상태

    @Builder
    public PaymentVerificationRequest(Long userId, Long lectureId, Long matchedLectureId,
                                      boolean success, String errorCode,
                                      String errorMessage, String impUid, String merchantUid,
                                      PaymentMethod payMethod, BigDecimal paidAmount, PaymentStatus status) {
        this.userId = userId;
        this.lectureId = lectureId;
        this.matchedLectureId = matchedLectureId;
        this.success = success;
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
        this.impUid = impUid;
        this.merchantUid = merchantUid;
        this.payMethod = payMethod;
        this.paidAmount = paidAmount;
        this.status = status;
    }
}
