package com.edu.application;

import com.edu.domain.dto.SendEmailEventQueue;
import com.edu.domain.entity.MatchedLecture;
import com.edu.domain.service.EmailMessageQueueService;
import com.edu.domain.dto.PaymentDto;
import com.edu.domain.enums.PaymentStatus;
import com.edu.domain.repository.PaymentJRepository;
import com.edu.domain.repository.MatchedLectureJRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Queue;

@Service
@RequiredArgsConstructor
public class MatchedLectureCheckSchedulerService {

    private final Logger log = LoggerFactory.getLogger(this.getClass().getSimpleName());
    private final MatchedLectureJRepository matchedLectureJRepository;
    private final PaymentJRepository paymentJRepository;
    private final EmailMessageQueueService emailMessageQueue;
    private final LocalDateTime now = LocalDateTime.now();
    private final ApplicationEventPublisher publisher;

    @Transactional
    public void checkIsPayComplete() {

        // startTime 전에 모든 lecture탐색
        List<MatchedLecture> matchedLectureList = matchedLectureJRepository.findAllLectureJoinWithStartTimeBeforeNow();
        Queue<Long> messageQueue = new LinkedList();

        for(MatchedLecture matchedLecture: matchedLectureList){
            PaymentDto paymentDto = paymentJRepository.findByMatchedLectureIdAndPaymentStatus(
                    matchedLecture.getMatchedLectureId(), PaymentStatus.PAID);

            if(Objects.isNull(paymentDto)){

                Duration duration = Duration.between(now, matchedLecture.getCreatedAt());
                if(validateIsNotPay(matchedLecture,duration)){
                    continue;
                }

                try{
                    emailMessageQueue.addMessageQueueForSendEmail(messageQueue,matchedLecture.getUserId()
                            ,duration,matchedLecture.getSendEmailCount());
                } catch (Exception e){
                    log.info("메세지 큐에 데이터를 담으면서 에러가 발생하였습니다.");
                    messageQueue.clear(); // TODO 요구사항: queue를 담으면서 에러가 발생하면 모두 전송 x, 에러는 에러이기에 지워주어야 한다.
                    e.printStackTrace();
                }
            }
        }

        log.info("이메일을 보낼 queue size: {}",messageQueue.size());
        publisher.publishEvent(new SendEmailEventQueue(messageQueue));
    }

    /**
     * 3일 이후면 soft delete 진행, 이메일 전송 x
     */
    private boolean validateIsNotPay(MatchedLecture matchedLecture, Duration duration) {
        if(Math.abs(duration.toMinutes()) >= 4320){
            matchedLecture.softDelete();
            return true;
        }
        return false;
    }
}
