package com.edu.domain.service;

import java.time.Duration;
import java.util.Queue;

public interface EmailMessageQueueService {

    void addMessageQueueForSendEmail(Queue<Long> messageQueue, Long userId, Duration duration, int sendEmailCount);
}
