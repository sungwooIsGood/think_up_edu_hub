package com.edu.domain.enums;

import lombok.Getter;

@Getter
public enum LectureType {

    MATH("수학"),
    GAME("게임"),
    ENGLISH("영어"),
    FITNESS("헬스"),

    COIN("코인");

    private final String lectureKrName;

    LectureType(String lectureKrName) {
        this.lectureKrName = lectureKrName;
    }
}
