package com.edu.domain.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PaymentCancelAnnotation {

    private String pgTid;        // PG사 승인취소번호 (필수)
    private double amount;       // 취소 금액 (필수)
    private int cancelledAt;     // 취소 시각 (필수)
    private String reason;       // 취소 사유 (필수)
    private String receiptUrl;   // 취소 매출전표 URL (선택)
}
