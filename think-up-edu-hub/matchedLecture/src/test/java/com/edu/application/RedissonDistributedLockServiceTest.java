package com.edu.application;

import com.edu.application.matchedLecture.RedissonDistributedLockService;
import com.edu.domain.dto.LectureRegisterRequest;
import com.edu.domain.dto.UserSignUpRequest;
import com.edu.domain.entity.Lecture;
import com.edu.domain.entity.User;
import com.edu.domain.enums.LectureType;
import com.edu.domain.enums.UserType;
import com.edu.domain.repository.LectureJRepository;
import com.edu.domain.matchedLecture.repository.MatchedLectureJRepository;
import com.edu.domain.repository.UserJRepository;
import com.edu.domain.value.LectureState;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@SpringBootTest
class RedissonDistributedLockServiceTest {

    @Autowired
    private LectureJRepository lectureJRepository;

    @Autowired
    private UserJRepository userJRepository;

    @Autowired
    private MatchedLectureJRepository matchedLectureJRepository;

    @Autowired
    private RedissonDistributedLockService redissonDistributedLockService;
    @Autowired
    private UserService userService;

    @Test
    void 수강신청_테스트() throws InterruptedException {

        // given
        ExecutorService executorService = Executors.newFixedThreadPool(100); // 스레드 100개 생성
        // 스레드가 작업을 완료할 때마다 카운트를 감소, TODO 병렬로 실행되는 여러 스레드에서 각각 작업을 수행하고, 모든 작업이 완료될 때까지 대기해야 하는 경우 사용
        CountDownLatch countDownLatch = new CountDownLatch(100);

        LectureRegisterRequest lectureRegisterRequest = new LectureRegisterRequest();
        lectureRegisterRequest.setTitle("과외 제목");
        lectureRegisterRequest.setDescription("과외 설명");
        lectureRegisterRequest.setCapacity(40);
        lectureRegisterRequest.setLectureState(LectureState.ACTIVE);
        lectureRegisterRequest.setStartDate(LocalDateTime.now());
        lectureRegisterRequest.setEndDate(LocalDateTime.now());
        lectureRegisterRequest.setPrice(BigDecimal.valueOf(60000));
        lectureRegisterRequest.setDiscount(false);
        lectureRegisterRequest.setDiscountRate(0.0);
        lectureRegisterRequest.setLectureType(LectureType.MATH);

        Long teacherId = 1L;
        Lecture lecture = Lecture.createdLecture(lectureRegisterRequest, teacherId);
        Long lectureId = lectureJRepository.save(lecture).getLectureId();

        List<User> studentList = saveManyStudent();
        List<Long> matchedLectureList = new ArrayList<>();

        // when
        studentList.forEach(student -> {
            executorService.submit(() ->{
                try{
                    Long lectureStudentId = redissonDistributedLockService.lock(student.getUserId(), lectureId);
                    matchedLectureList.add(lectureStudentId);
                } catch (Throwable e) {
                    throw new RuntimeException(e);
                } finally {
                    countDownLatch.countDown();
                }
            });
        });

        countDownLatch.await();

        // then
        Assertions.assertThat(matchedLectureList.size()).isEqualTo(40);

    }

    private List<User> saveManyStudent() {

        for(int i = 1; i < 100; i++){

            UserSignUpRequest userSignUpRequest = new UserSignUpRequest();
            userSignUpRequest.setName("학생 "+ i);
            userSignUpRequest.setBirthday("2023-11-11T12:12:12");
            userSignUpRequest.setGender("남성");
            userSignUpRequest.setPhoneNumber("010-5555-5555");
            userSignUpRequest.setAddress("경기도");
            userSignUpRequest.setUsername("username " + i);
            userSignUpRequest.setPassword("11");
            userSignUpRequest.setUserType(UserType.STUDENT);

            userService.signUp(userSignUpRequest);
        }
        return userJRepository.findAll();
    }

}