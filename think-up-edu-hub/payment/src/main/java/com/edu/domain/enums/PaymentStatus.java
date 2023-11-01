package com.edu.domain.enums;

public enum PaymentStatus {
    READY("ready"),
    PAID("paid"),
    FAILED("failed");

    private final String code;

    PaymentStatus(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
