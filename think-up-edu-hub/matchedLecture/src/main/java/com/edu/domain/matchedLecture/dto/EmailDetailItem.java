package com.edu.domain.matchedLecture.dto;

import com.edu.domain.entity.Lecture;
import com.edu.domain.entity.User;
import com.edu.domain.matchedLecture.entity.MatchedLecture;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@NoArgsConstructor
@Getter
public class EmailDetailItem {

    private String name;
    private String title;
    private BigDecimal price;
    private String lectureType;
    private int sendEmailCount;

    @Builder
    public EmailDetailItem(String name, String title, BigDecimal price,
                           String lectureType, int sendEmailCount) {
        this.name = name;
        this.title = title;
        this.price = price;
        this.lectureType = lectureType;
        this.sendEmailCount = sendEmailCount;
    }

    public EmailDetailItem(MatchedLecture matchedLecture,
                           User user,
                           Lecture lecture) {
        this.name = user.getUserInfo().getName();
        this.title = lecture.getLectureInfo().getTitle();
        this.price = lecture.getPriceInfo().getPrice();
        this.lectureType = lecture.getLectureInfo().getLectureType().getLectureKrName();
        this.sendEmailCount = matchedLecture.getSendEmailCount();
    }


}
