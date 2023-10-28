package com.edu.application.matchedLecture.scheduler;

import com.edu.application.matchedLecture.MatchedLectureSchedulerService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MatchedLecturePayCheckScheduler {

    private final MatchedLectureSchedulerService matchedLectureSchedulerService;

//    @Scheduled(cron = "* */10 * * * *")
    public void payCheckScheduler(){
        matchedLectureSchedulerService.checkIsPayComplete();
    }
}
