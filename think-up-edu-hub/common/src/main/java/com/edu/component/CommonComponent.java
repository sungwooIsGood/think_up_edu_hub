package com.edu.component;

import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Component
public class CommonComponent {

    public static LocalDateTime convertTimestampToLocalDateTime(Long timestamp){
        long epochTimestamp = timestamp * 1000L;
        LocalDateTime localDateTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(epochTimestamp), ZoneOffset.UTC);
        return localDateTime;
    }

}
