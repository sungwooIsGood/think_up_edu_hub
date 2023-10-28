package com.edu.presentation.controller.payment;

import com.edu.application.payment.PaymentService;
import com.edu.domain.payment.dto.PaymentVerificationRequest;
import com.edu.entity.BasicResponse;
import com.edu.infrastructure.aspect.JwtHeaderReader;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/payment")
public class PaymentController {

    private final PaymentService paymentService;

    /**
     * 결제 완료 검증
     */
    @PostMapping("/verification")
    public BasicResponse verifyPayment(PaymentVerificationRequest paymentVerificationRequest){

        Map<String,Object> responseData = new HashMap<>();

        paymentService.verifyPayment(paymentVerificationRequest);

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
