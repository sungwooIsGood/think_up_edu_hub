package com.edu.domain.enums;

public enum PaymentMethod {
    CARD("card"),
    TRANS("trans"),
    VBANK("vbank"),
    PHONE("phone"),
    CULTURELAND("cultureland"),
    SMARTCULTURE("smartculture"),
    HAPPYMONEY("happymoney"),
    BOOKNLIFE("booknlife"),
    CULTUREGIFT("culturegift"),
    SAMSUNG("samsung"),
    KAKAOPAY("kakaopay"),
    NAVERPAY("naverpay"),
    PAYCO("payco"),
    LPAY("lpay"),
    SSGPAY("ssgpay"),
    TOSSPAY("tosspay"),
    APPLEPAY("applepay"),
    PINPAY("pinpay"),
    SKPAY("skpay"),
    WECHAT("wechat"),
    ALIPAY("alipay"),
    UNIONPAY("unionpay"),
    TENPAY("tenpay"),
    PAYSBUY("paysbuy"),
    ECONTEXT("econtext"),
    MOLPAY("molpay"),
    POINT("point"),
    PAYPAL("paypal"),
    TOSS_BRANDPAY("toss_brandpay"),
    NAVERPAY_CARD("naverpay_card"),
    NAVERPAY_POINT("naverpay_point");

    private final String code;

    PaymentMethod(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}

