package com.edu.domain.entity;

import com.edu.domain.value.LectureDuration;
import com.edu.domain.value.LectureInfo;
import com.edu.domain.value.PriceInfo;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.edu.BaseEntity;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@Getter
public class Lecture extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long lectureId;
    private Long userId;

    @Embedded
    private LectureInfo lectureInfo;

    @Embedded
    private LectureDuration lectureDuration;

    @Embedded
    private PriceInfo priceInfo;


}
