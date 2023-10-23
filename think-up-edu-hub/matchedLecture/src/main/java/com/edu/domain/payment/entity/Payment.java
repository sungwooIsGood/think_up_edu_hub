package com.edu.domain.payment.entity;

import com.edu.entity.BaseEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@Getter
public class Payment extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long paymentId;
    private Long matchedLectureId;
    private Long lectureId;
    private Long userId;
    private Long payDay;
    private Long price;
}
