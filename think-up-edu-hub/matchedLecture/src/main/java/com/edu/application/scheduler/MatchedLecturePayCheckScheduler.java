package com.edu.application.scheduler;

import com.edu.application.MatchedLectureSchedulerService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MatchedLecturePayCheckScheduler {

    private final MatchedLectureSchedulerService matchedLectureSchedulerService;

    @Scheduled(cron = "0/20 * * * * *")
    public void payCheckScheduler(){
        matchedLectureSchedulerService.checkIsPayComplete();
    }
}
