package com.edu.domain.payment.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Getter
@NoArgsConstructor
public class BeforePaymentVerificationItem {

    private String merchantUuid;
    private BigDecimal amount;

    @Builder
    public BeforePaymentVerificationItem(String merchantUuid, BigDecimal amount) {
        this.merchantUuid = merchantUuid;
        this.amount = amount;
    }
}
