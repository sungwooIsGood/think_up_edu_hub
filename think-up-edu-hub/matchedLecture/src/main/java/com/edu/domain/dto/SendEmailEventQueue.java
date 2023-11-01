package com.edu.domain.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Queue;

@Getter
@NoArgsConstructor
public class SendEmailEventQueue {

    private Queue<Long> messageQueue;

    @Builder
    public SendEmailEventQueue(Queue<Long> messageQueue) {
        this.messageQueue = messageQueue;
    }
}
