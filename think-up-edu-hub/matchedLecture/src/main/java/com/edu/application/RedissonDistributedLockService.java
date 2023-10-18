package com.edu.application;

import com.edu.exception.custom.RedissonLockException;
import lombok.RequiredArgsConstructor;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class RedissonDistributedLockService {

    private final RedissonClient redissonClient;
    private final MatchedLectureService matchedLectureService;
    private final Logger log = LoggerFactory.getLogger(this.getClass().getSimpleName());
    @Value("${redis.distributed.rock}")
    private String rockName;
    private Long waitTime = 5L;
    private Long leaseTime = 5L;
    private TimeUnit timeUnit = TimeUnit.SECONDS;

    @Transactional
    public Long lock(Long userId, Long lectureId) throws Throwable{

        RLock rLock = redissonClient.getLock(rockName);

        try{
            boolean successGetLock = rLock.tryLock(waitTime,leaseTime,timeUnit);

            if (!successGetLock) {
                throw new RedissonLockException("과외 신청하면서 선착순 실패");
            }

            return matchedLectureService.isExceedThNumberOfCapacity(userId,lectureId);

        } catch (InterruptedException e) {
            log.info("멀티 스레드 환경에서 예외가 발생하였습니다.");
            throw new InterruptedException();
        } finally {
            try {
                rLock.unlock();
            } catch (IllegalMonitorStateException e) {
                log.info("락을 해제하면서 예외가 발생하였습니다.");
                throw new IllegalMonitorStateException();
            }

        }
    }
}
