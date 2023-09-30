package com.edu.domain.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.edu.BaseEntity;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@Getter
public class Payment extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long paymentId;
    private Long lectureId;
    private Long userId;
    private Long payDay;
    private Long price;
}
