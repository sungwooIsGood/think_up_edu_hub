package com.edu.domain.payment.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Getter
@NoArgsConstructor
public class PaymentCancelRequest {

    @NotNull(message = "impUid 값은 필수")
    private String impUid;
    @NotNull(message = "merchantUid 값은 필수")
    private String merchantUid;
    @NotNull(message = "환불 금액을 소수점까지 다 입력해주세요.")
    private BigDecimal cancelPrice;
    private String cancelReason;
}
