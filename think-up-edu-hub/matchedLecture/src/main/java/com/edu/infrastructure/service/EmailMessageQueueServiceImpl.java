package com.edu.infrastructure.service;

import com.edu.domain.service.EmailMessageQueueService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.util.Queue;

@Service
@RequiredArgsConstructor
public class EmailMessageQueueServiceImpl implements EmailMessageQueueService {

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void addMessageQueueForSendEmail(Queue<Long> messageQueue, Long userId, Duration duration, int sendEmailCount) {

        if(Math.abs(duration.toDays()) != sendEmailCount) {
            messageQueue.add(userId);
        }
    }
}
