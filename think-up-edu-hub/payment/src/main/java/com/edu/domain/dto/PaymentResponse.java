package com.edu.domain.dto;

import com.edu.component.CommonComponent;
import com.edu.domain.entity.Payment;
import com.edu.domain.enums.PaymentMethod;
import com.edu.domain.enums.PaymentStatus;
import com.google.gson.annotations.SerializedName;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Getter
@NoArgsConstructor
public class PaymentResponse {

    private int code;
    private String message;
    @SerializedName("response")
    private ResponseData responseData;

    @Getter
    public static class ResponseData {
        @SerializedName("imp_uid")
        private String impUid; // 포트원 거래고유번호 (필수)

        @SerializedName("merchant_uid")
        private String merchantUid; // 가맹점 주문번호 (필수)

        @SerializedName("pay_method")
        private PaymentMethod payMethod; // 결제수단 구분코드 (선택)

        @SerializedName("channel")
        private String channel; // 결제환경 구분코드 (선택)

        @SerializedName("pg_provider")
        private String pgProvider; // PG사 구분코드 (선택)

        @SerializedName("emb_pg_provider")
        private String embPgProvider; // 허브형결제 PG사 구분코드 (선택)

        @SerializedName("pg_tid")
        private String pgTid; // PG사 거래번호 (선택)

        @SerializedName("pg_id")
        private String pgId; // PG사 상점아이디 (선택)

        @SerializedName("escrow")
        private boolean escrow; // 에스크로결제 여부 (선택)

        @SerializedName("apply_num")
        private String applyNum; // 승인번호 (선택)

        @SerializedName("bank_code")
        private String bankCode; // 은행 표준코드 (선택)

        @SerializedName("bank_name")
        private String bankName; // 은행명 (선택)

        @SerializedName("card_code")
        private String cardCode; // 카드사 코드번호 (선택)

        @SerializedName("card_name")
        private String cardName; // 카드사명 (선택)

        @SerializedName("card_quota")
        private int cardQuota; // 할부개월 수 (선택)

        @SerializedName("card_number")
        private String cardNumber; // 카드번호 (선택)

        @SerializedName("card_type")
        private int cardType; // 카드 구분코드 (선택)

        @SerializedName("vbank_code")
        private String vbankCode; // 가상계좌 은행 표준코드 (선택)

        @SerializedName("vbank_name")
        private String vbankName; // 가상계좌 은행명 (선택)

        @SerializedName("vbank_num")
        private String vbankNum; // 가상계좌 계좌번호 (선택)

        @SerializedName("vbank_holder")
        private String vbankHolder; // 가상계좌 예금주 (선택)

        @SerializedName("vbank_date")
        private int vbankDate; // 가상계좌 입금기한 (선택)

        @SerializedName("vbank_issued_at")
        private int vbankIssuedAt; // 가상계좌 생성시각 (선택)

        @SerializedName("name")
        private String name; // 제품명 (선택)

        @SerializedName("amount")
        private BigDecimal amount; // 결제금액 (필수)

        @SerializedName("cancel_amount")
        private double cancelAmount; // 취소금액 (필수)

        @SerializedName("currency")
        private String currency; // 결제통화 구분코드 (필수)

        @SerializedName("buyer_name")
        private String buyerName; // 주문자명 (선택)

        @SerializedName("buyer_email")
        private String buyerEmail; // 주문자 Email주소 (선택)

        @SerializedName("buyer_tel")
        private String buyerTel; // 주문자 전화번호 (선택)

        @SerializedName("buyer_addr")
        private String buyerAddr; // 주문자 주소 (선택)

        @SerializedName("buyer_postcode")
        private String buyerPostcode; // 주문자 우편번호 (선택)

        @SerializedName("custom_data")
        private String customData; // 추가정보 (선택)

        @SerializedName("user_agent")
        private String userAgent; // 단말기의 UserAgent 문자열 (선택)

        @SerializedName("status")
        private PaymentStatus status; // 결제상태 (필수)

        @SerializedName("started_at")
        private int startedAt; // 요청 시각 (선택)

        @SerializedName("paid_at")
        private Long paidAt; // 결제 시각 (선택)

        @SerializedName("failed_at")
        private Long failedAt; // 실패시각 (선택)

        @SerializedName("cancelled_at")
        private Long cancelledAt; // 취소시각 (선택)

        @SerializedName("fail_reason")
        private String failReason; // 결제실패 사유
    }

    public Payment createdPayment(Long userId,Long matchedLectureId,Long lectureId){
        return Payment.builder()
                .userId(userId)
                .matchedLectureId(matchedLectureId)
                .lectureId(lectureId)
                .payDay(CommonComponent.convertTimestampToLocalDateTime(this.responseData.paidAt))
                .price(responseData.amount)
                .payMethod(responseData.payMethod)
                .paymentStatus(this.responseData.status)
                .impUid(this.responseData.impUid)
                .merchantUid(this.responseData.merchantUid)
                .build();
    }
}
