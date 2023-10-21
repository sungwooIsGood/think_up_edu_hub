package com.edu.infrastructure.scheduler;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MatchedLecturePayCheckScheduler {

    private final Logger log = LoggerFactory.getLogger(this.getClass().getSimpleName());

    @Scheduled(cron = "0 0/1 * * * *")
    public void test(){
        // startTime 전에 모든 lecture와 matchedLecture join 탐색
        // 탐색 된 userId 가지고 payment db에 특정 가 있는지 확인
        // 없으면 이메일 보내기
        // 3일이 넘었으면 matchedLecture에서 소프트 딜리트 진행
    }
}
