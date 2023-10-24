package com.edu.infrastructure.database.querydsl;

import com.edu.domain.matchedLecture.service.EmailMessageQueue;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.util.Queue;

@Service
@RequiredArgsConstructor
public class EmailMessageQueueImpl implements EmailMessageQueue {

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void addMessageQueueForSendEmail(Queue<Long> messageQueue, Long userId, Duration duration, int sendEmailCount) {

        if(duration.toDays() != sendEmailCount) {
            messageQueue.add(userId);
        }
    }
}
