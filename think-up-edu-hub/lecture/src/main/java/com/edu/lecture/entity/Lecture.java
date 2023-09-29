package com.edu.lecture.entity;

import com.edu.lecture.value.LectureDuration;
import com.edu.lecture.value.LectureInfo;
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


}
