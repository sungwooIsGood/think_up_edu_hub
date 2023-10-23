package com.edu.infrastructure.database.querydsl;

import com.edu.domain.matchedLecture.service.SendEmailService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SendEmailServiceImpl implements SendEmailService {

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void sendEmail() {

    }
}
