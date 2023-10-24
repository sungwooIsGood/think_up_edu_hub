package com.edu.infrastructure.events;

import com.edu.domain.matchedLecture.dto.EmailDetailItem;
import com.edu.domain.matchedLecture.dto.SendEmailEvent;
import com.edu.domain.matchedLecture.repository.MatchedLectureJRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

import java.util.Queue;

@Component
@RequiredArgsConstructor
public class SendEmailEventHandler {

    private final MatchedLectureJRepository matchedLectureJRepository;

    @Async
    @Transactional(propagation = Propagation.REQUIRES_NEW) // after commit이라고는 하지만 한 트랜잭션으로 묶여있기 때문에 분리 시켜야함. 안그러면 update, delete 등 실행 안됨.
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void validateAfterSendEmail(SendEmailEvent sendEmailEvent) {

        Queue<Long> messageQueue = sendEmailEvent.getMessageQueue();

        while (!messageQueue.isEmpty()){
            Long userId = messageQueue.poll();

            EmailDetailItem emailDetailItem = matchedLectureJRepository.getEmailDetailItem(userId);

            // send email
            // matchedLecture.sendEmailCountUp(); - update 쿼리로 바꾸자.
            matchedLectureJRepository.updateSendEmailCountPlus(userId);
        }
    }
}
