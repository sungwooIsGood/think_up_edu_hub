package com.edu.domain.value;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import java.math.BigDecimal;

@Embeddable
@NoArgsConstructor
@Getter
public class PriceInfo {

    private BigDecimal price;
    private boolean isDiscount;
    private Double discountRate;

    @Builder
    public PriceInfo(BigDecimal price, boolean isDiscount, Double discountRate) {
        this.price = price;
        this.isDiscount = isDiscount;
        this.discountRate = discountRate;
    }
}
