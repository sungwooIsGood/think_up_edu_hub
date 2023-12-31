package com.edu.application;

import com.edu.domain.dto.*;
import com.edu.domain.entity.Payment;
import com.edu.domain.enums.PaymentStatus;
import com.edu.domain.repository.PaymentJRepository;
import com.edu.domain.service.ExternalPaymentService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.util.Objects;

@RestController
@RequiredArgsConstructor
public class PaymentService {

    private final Logger log = LoggerFactory.getLogger(this.getClass().getSimpleName());
    private final ExternalPaymentService externalPaymentService;
    private final PaymentJRepository paymentJRepository;

    @Transactional
    public PaymentResponse verifyPayment(PaymentVerificationRequest paymentVerificationRequest) {

        if(!paymentVerificationRequest.isSuccess()){
            log.info("결제가 취소된 값이 넘어왔습니다. PaymentStatus: {}, error_code: {}, error_message: {}",
                    paymentVerificationRequest.getStatus()
                    ,paymentVerificationRequest.getErrorCode()
                    ,paymentVerificationRequest.getErrorMessage());
            throw new IllegalStateException("결제에 실패한 값이 넘어왔습니다. PaymentStatus: " + paymentVerificationRequest.getStatus().getCode());
        }

        verifyAlreadyPaymentCompleted(paymentVerificationRequest.getUserId());

        PaymentResponse paymentResponse = externalPaymentService.sendVerifyPaymentRequest(paymentVerificationRequest.getImpUid());

        Payment payment = paymentResponse.createdPayment(paymentVerificationRequest.getUserId(),
                paymentVerificationRequest.getMatchedLectureId(),
                paymentVerificationRequest.getLectureId());

        paymentJRepository.save(payment);

        return paymentResponse;
    }

    private void verifyAlreadyPaymentCompleted(Long userId) {

        PaymentDto paymentDto = paymentJRepository.findByMatchedLectureIdAndPaymentStatus(userId, PaymentStatus.PAID);

        if(Objects.nonNull(paymentDto)){
            throw new IllegalStateException("이미 결제가 되어있는 이용자 입니다. userId: " + userId);
        }

    }

    @Transactional(readOnly = true)
    public void beforeCheckPayment(BeforePaymentVerificationItem beforePaymentVerificationRequest) {
        BeforePaymentVerificationItem beforePaymentVerificationItem = externalPaymentService.verifyBeforePayment(beforePaymentVerificationRequest);

        String merchantUuid = beforePaymentVerificationRequest.getMerchantUuid();
        BigDecimal amount = beforePaymentVerificationRequest.getAmount();

        if(!merchantUuid.equals(beforePaymentVerificationItem.getMerchantUuid())){
            throw new IllegalStateException("merchant_uuid 값이 다릅니다.");
        }

        if(!amount.equals(beforePaymentVerificationItem.getAmount())){
            throw new IllegalStateException("저장되어있는 가격의 정보와 다릅니다.");
        }

        PaymentDto paymentDto = paymentJRepository.findByPriceAndMerchantUid(amount, merchantUuid);

        if(!merchantUuid.equals(paymentDto.getMatchedLectureId()) || !amount.equals(paymentDto.getPrice())){
            log.info("request 값, merchant_uid: {}, amount: {}, / DB 값: merchant_uid: {}, amount: {}",
                    merchantUuid,amount,paymentDto.getMatchedLectureId(),paymentDto.getPrice());
            throw new IllegalStateException("DB에 저장 되어있는 가격의 정보와 다릅니다.");
        }
    }

    @Transactional
    public void cancelPayment(PaymentCancelRequest paymentCancelRequest) {
        Payment payment = paymentJRepository.findByImpUidAndMerchantUid(paymentCancelRequest.getImpUid()
                , paymentCancelRequest.getMerchantUid());

        if(paymentCancelRequest.getCancelPrice().equals(payment.getPrice())){
            throw new IllegalStateException("입력한 환불금액과 결제한 금액이 다릅니다.");
        }

        PaymentResponse paymentResponse = externalPaymentService.cancelPayment(paymentCancelRequest);

        payment.cancelPayment(paymentResponse.getResponseData().getCancelledAt(),paymentResponse.getResponseData().getStatus());
    }
}
