package com.edu.infrastructure.aspect.redis;

import com.edu.exception.custom.RedissonLockException;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

@Component
@Aspect
@RequiredArgsConstructor
public class RedissonDistributedLockAspect {

    private final RedissonClient redissonClient;
    private final Logger log = LoggerFactory.getLogger(this.getClass().getSimpleName());
    @Value("${redis.distributed.rock}")
    private String rockName;

    @Around("@annotation(DistributedLock)")
    public Object lock(ProceedingJoinPoint jp) throws Throwable{

        MethodSignature signature = (MethodSignature) jp.getSignature(); // 현재 메서드의 시그니처 가져오기
        Method method = signature.getMethod(); // 타겟 메서드 가져오기
        DistributedLock distributedLock = method.getAnnotation(DistributedLock.class); //
        RLock rLock = redissonClient.getLock(rockName);

        try{
            boolean successGetLock = rLock.tryLock(distributedLock.waitTime(),distributedLock.leaseTime(),distributedLock.timeUnit());

            if (!successGetLock) {
                throw new RedissonLockException("과외 신청하면서 선착순 실패");
            }

            return jp.proceed();

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
