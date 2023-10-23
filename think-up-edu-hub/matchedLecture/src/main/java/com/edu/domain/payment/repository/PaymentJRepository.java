package com.edu.domain.payment.repository;

import com.edu.domain.payment.dto.PaymentDto;
import com.edu.domain.payment.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;


public interface PaymentJRepository extends JpaRepository<Payment,Long> {

    PaymentDto findByMatchedLectureId(Long matchedLectureId);
}
