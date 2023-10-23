package com.edu.application;

import com.edu.domain.matchedLecture.entity.MatchedLecture;
import com.edu.domain.matchedLecture.service.SendEmailService;
import com.edu.domain.payment.dto.PaymentDto;
import com.edu.domain.payment.repository.PaymentJRepository;
import com.edu.domain.repository.LectureJRepository;
import com.edu.domain.matchedLecture.repository.MatchedLectureJRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class MatchedLectureSchedulerService {

    private final Logger log = LoggerFactory.getLogger(this.getClass().getSimpleName());
    private final LectureJRepository lectureJRepository;
    private final MatchedLectureJRepository matchedLectureJRepository;
    private final PaymentJRepository paymentJRepository;
    private final SendEmailService sendEmailService;
    private final LocalDateTime now = LocalDateTime.now();

    @Transactional
    public void checkIsPayComplete() {


        // startTime 전에 모든 lecture탐색
        List<MatchedLecture> matchedLectureList = matchedLectureJRepository.findAllLectureJoinWithStartTimeBeforeNow();

        matchedLectureList.stream()
                .forEach(matchedLecture -> {

                    // 1. 페이먼츠에 없으면
                    PaymentDto paymentDto = paymentJRepository.findByMatchedLectureId(matchedLecture.getMatchedLectureId());

                    if(Objects.isNull(paymentDto)){


                        validateIsPay(matchedLecture);

                        // 3. 이메일 발송
                        validateSendEmail(matchedLecture);
                    }
                });
    }

    /**
     * TODO 깊은 복사 시 엔티티 복사 어떻게 되는지 테스트 해야함.
     * @param matchedLecture
     */
    private void validateIsPay(MatchedLecture matchedLecture) {
        MatchedLecture matchedLectureCopy = matchedLecture.copyMatchedLecture(matchedLecture); // TODO 굳이 해야한?
        // 2. 현재시간과 비교해서 matchedLecture 데이터가 3일이 넘었으면 matchedLecture에서 소프트 딜리트 진행
        Duration threeDayDurationCheck = Duration.between(now, matchedLectureCopy.getCreatedAt());
        if(threeDayDurationCheck.toMinutes() >= 4320){
            matchedLectureCopy.softDelete();
        }
    }

    private void validateSendEmail(MatchedLecture matchedLecture) {
        Duration oneDayDurationCheck = Duration.between(now.toLocalDate(),matchedLecture.getCreatedAt());
        if(oneDayDurationCheck.toDays() != matchedLecture.getSendEmailCount()){
            sendEmailService.sendEmail();
            matchedLecture.sendEmailCountUp();
        }
    }
}
