package com.edu.domain.payment.repository;

import com.edu.domain.payment.dto.PaymentDto;
import com.edu.domain.payment.entity.Payment;
import com.edu.domain.payment.enums.PaymentStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;


public interface PaymentJRepository extends JpaRepository<Payment,Long> {

    @Query("SELECT new com.edu.domain.payment.dto.PaymentDto(p) FROM Payment p WHERE p.matchedLectureId = :matchedLectureId AND p.paymentStatus = :paymentStatus")
    PaymentDto findByMatchedLectureIdAndPaymentStatus(Long matchedLectureId, PaymentStatus paymentStatus);

    @Query("SELECT new com.edu.domain.payment.dto.PaymentDto(p) FROM Payment p WHERE p.price = :price AND p.merchantUid = :merchantUid")
    PaymentDto findByPriceAndMerchantUid(BigDecimal price, String merchantUid);

    Payment findByImpUidAndMerchantUid(String impUid,String merchantUid);
}
