package com.edu.domain.dto;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
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
