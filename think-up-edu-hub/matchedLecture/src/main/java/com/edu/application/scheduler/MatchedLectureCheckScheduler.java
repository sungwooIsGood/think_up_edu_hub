package com.edu.application.scheduler;

import com.edu.application.MatchedLectureCheckSchedulerService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MatchedLectureCheckScheduler {

    private final MatchedLectureCheckSchedulerService matchedLectureCheckSchedulerService;

    @Scheduled(cron = "* 0/10 * * * *")
    public void payCheckScheduler(){
        matchedLectureCheckSchedulerService.checkIsPayComplete();
    }
}
