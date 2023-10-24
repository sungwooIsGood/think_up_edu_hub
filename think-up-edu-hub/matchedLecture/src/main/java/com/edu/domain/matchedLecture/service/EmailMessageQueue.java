package com.edu.domain.matchedLecture.service;

import java.time.Duration;
import java.util.Queue;

public interface EmailMessageQueue {

    void addMessageQueueForSendEmail(Queue<Long> messageQueue, Long userId, Duration duration, int sendEmailCount);
}
