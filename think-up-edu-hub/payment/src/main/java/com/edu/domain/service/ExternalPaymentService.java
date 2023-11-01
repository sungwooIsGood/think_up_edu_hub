package com.edu.domain.service;

import com.edu.domain.dto.AccessTokenResponse;
import com.edu.domain.dto.BeforePaymentVerificationItem;
import com.edu.domain.dto.PaymentCancelRequest;
import com.edu.domain.dto.PaymentResponse;

import java.io.UnsupportedEncodingException;

public interface ExternalPaymentService {

    AccessTokenResponse getAccessToken() throws UnsupportedEncodingException;

    PaymentResponse sendVerifyPaymentRequest(String impUid);

    BeforePaymentVerificationItem verifyBeforePayment(BeforePaymentVerificationItem beforePaymentVerificationRequest);

    PaymentResponse cancelPayment(PaymentCancelRequest paymentCancelRequest);
}
