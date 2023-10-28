package com.edu.domain.payment.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class PaymentResponse {

    private String impUid;            // 포트원 거래 고유번호 (필수)
    private String merchantUid;       // 가맹점 주문번호 (필수)
    private String payMethod;         // 결제수단 구분코드 (선택)
    private String channel;           // 결제환경 구분코드 (선택)
    private String pgProvider;        // PG사 구분코드 (선택)
    private String embPgProvider;     // 허브형 결제 PG사 구분코드 (선택)
    private String pgTid;             // PG사 거래번호 (선택)
    private String pgId;              // PG사 상점아이디 (선택)
    private boolean escrow;           // 에스크로결제 여부 (선택)
    private String applyNum;          // 승인번호 (선택)
    private String bankCode;          // 은행 표준코드 (선택)
    private String bankName;          // 은행명 (선택)
    private String cardCode;          // 카드사 코드번호 (선택)
    private String cardName;          // 카드사명 (선택)
    private int cardQuota;            // 할부개월 수 (선택)
    private String cardNumber;        // 카드번호 (선택)
    private int cardType;             // 카드 구분코드 (선택)
    private String vbankCode;         // 가상계좌 은행 표준코드 (선택)
    private String vbankName;         // 가상계좌 은행명 (선택)
    private String vbankNum;          // 가상계좌 계좌번호 (선택)
    private String vbankHolder;       // 가상계좌 예금주 (선택)
    private int vbankDate;            // 가상계좌 입금기한 (선택)
    private int vbankIssuedAt;        // 가상계좌 생성시각 (선택)
    private String name;              // 제품명 (선택)
    private double amount;            // 결제금액 (필수)
    private double cancelAmount;      // 취소금액 (필수)
    private String currency;          // 결제통화 구분코드 (필수)
    private String buyerName;         // 주문자명 (선택)
    private String buyerEmail;        // 주문자 Email주소 (선택)
    private String buyerTel;          // 주문자 전화번호 (선택)
    private String buyerAddr;         // 주문자 주소 (선택)
    private String buyerPostcode;     // 주문자 우편번호 (선택)
    private String customData;        // 추가정보 (선택)
    private String userAgent;         // 단말기의 UserAgent 문자열 (선택)
    private String status;            // 결제상태 (필수)
    private int startedAt;            // 요청 시각 (선택)
    private int paidAt;               // 결제 시각 (선택)
    private int failedAt;             // 실패 시각 (선택)
    private int cancelledAt;          // 취소 시각 (선택)
    private String failReason;        // 결제실패 사유 (선택)
    private String cancelReason;      // 결제취소 사유 (선택)
    private String receiptUrl;        // 매출전표 URL (선택)
    private List<PaymentCancelAnnotation> cancelHistory; // 취소 내역 (선택)
    private List<String> cancelReceiptUrls; // 취소/부분취소 시 생성되는 취소 매출전표 확인 URL (선택)
    private boolean cashReceiptIssued; // 현금영수증 발급 여부 (선택)
    private String customerUid;       // 구매자의 결제 수단 식별 고유번호 (선택)
    private String customerUidUsage;  // 구매자의 결제 수단 식별 고유번호 사용 구분코드 (선택)

}
