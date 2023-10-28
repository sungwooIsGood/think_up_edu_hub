package com.edu.application.payment;

import com.edu.domain.payment.dto.PaymentDto;
import com.edu.domain.payment.dto.PaymentResponse;
import com.edu.domain.payment.dto.PaymentVerificationRequest;
import com.edu.domain.payment.enums.PaymentStatus;
import com.edu.domain.payment.repository.PaymentJRepository;
import com.edu.domain.payment.service.ExternalPaymentService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.UnsupportedEncodingException;
import java.util.Objects;

@RestController
@RequiredArgsConstructor
public class PaymentService {

    private final Logger log = LoggerFactory.getLogger(this.getClass().getSimpleName());
    private final ExternalPaymentService externalPaymentService;
    private final PaymentJRepository paymentJRepository;

    @GetMapping("/test")
    public String test() throws UnsupportedEncodingException {
        externalPaymentService.getAccessToken();
        return "dd";
    }

    @Transactional
    public void verifyPayment(PaymentVerificationRequest paymentVerificationRequest) {

        if(!paymentVerificationRequest.isSuccess()){
            log.info("결제가 취소된 값이 넘어왔습니다. PaymentStatus: {}, error_code: {}, error_message: {}",
                    paymentVerificationRequest.getStatus(),paymentVerificationRequest.getErrorCode(),paymentVerificationRequest.getErrorMessage());
            throw new IllegalStateException("결제에 실패한 값이 넘어왔습니다. PaymentStatus: " + paymentVerificationRequest.getStatus().getCode());
        }

        verifyAlreadyPaymentCompleted(paymentVerificationRequest.getUserId());

        PaymentResponse paymentResponse = externalPaymentService.sendVerifyPaymentRequest(paymentVerificationRequest.getImpUid());

        System.out.println(paymentResponse);
//            paymentJRepository.

    }

    private void verifyAlreadyPaymentCompleted(Long userId) {

        PaymentDto paymentDto = paymentJRepository.findByMatchedLectureIdAndPaymentStatus(userId, PaymentStatus.PAID);

        if(Objects.nonNull(paymentDto)){
            throw new IllegalStateException("이미 결제가 되어있는 이용자 입니다. userId: " + userId);
        }

    }
}
