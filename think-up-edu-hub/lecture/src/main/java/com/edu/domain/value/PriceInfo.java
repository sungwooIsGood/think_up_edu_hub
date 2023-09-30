package com.edu.domain.value;

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
}
