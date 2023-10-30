package com.edu.presentation.controller.payment;

import com.edu.application.payment.PaymentService;
import com.edu.domain.payment.dto.BeforePaymentVerificationItem;
import com.edu.domain.payment.dto.PaymentResponse;
import com.edu.domain.payment.dto.PaymentVerificationRequest;
import com.edu.entity.BasicResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/payment")
public class PaymentController {

    private final PaymentService paymentService;

    /**
     * 결제 완료 전 사전 검증
     */
    public BasicResponse beforeCheckPayment(BeforePaymentVerificationItem beforePaymentVerificationRequest){

        paymentService.beforeCheckPayment(beforePaymentVerificationRequest);

        return BasicResponse.builder()
                .status(HttpStatus.OK.value())
                .build();
    }

    /**
     * 결제 완료 사후 검증
     */
    @PostMapping("/verification")
    public BasicResponse verifyPayment(PaymentVerificationRequest paymentVerificationRequest){

        Map<String,Object> responseData = new HashMap<>();

        PaymentResponse response = paymentService.verifyPayment(paymentVerificationRequest);

        responseData.put("imp_uid",response.getResponseData().getImpUid());
        responseData.put("merchant_uid",response.getResponseData().getMerchantUid());

        return BasicResponse.builder()
                .status(HttpStatus.OK.value())
                .data(responseData)
                .build();
    }


    /**
     * 결제 취소
     */
    @PostMapping("/cancel")
    public BasicResponse doPaymentCancel(){
        return null;
    }
}
