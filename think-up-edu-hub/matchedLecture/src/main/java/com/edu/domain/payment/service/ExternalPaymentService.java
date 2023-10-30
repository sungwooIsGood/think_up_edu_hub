package com.edu.domain.payment.service;

import com.edu.domain.payment.dto.BeforePaymentVerificationItem;
import com.edu.domain.payment.dto.AccessTokenResponse;
import com.edu.domain.payment.dto.PaymentCancelRequest;
import com.edu.domain.payment.dto.PaymentResponse;

import java.io.UnsupportedEncodingException;

public interface ExternalPaymentService {

    AccessTokenResponse getAccessToken() throws UnsupportedEncodingException;

    PaymentResponse sendVerifyPaymentRequest(String impUid);

    BeforePaymentVerificationItem verifyBeforePayment(BeforePaymentVerificationItem beforePaymentVerificationRequest);

    PaymentResponse cancelPayment(PaymentCancelRequest paymentCancelRequest);
}
