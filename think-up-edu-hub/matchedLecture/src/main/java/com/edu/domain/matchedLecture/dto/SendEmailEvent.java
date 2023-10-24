package com.edu.domain.matchedLecture.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Queue;

@Getter
@NoArgsConstructor
public class SendEmailEvent {

    private Queue<Long> messageQueue;

    @Builder
    public SendEmailEvent(Queue<Long> messageQueue) {
        this.messageQueue = messageQueue;
    }
}
