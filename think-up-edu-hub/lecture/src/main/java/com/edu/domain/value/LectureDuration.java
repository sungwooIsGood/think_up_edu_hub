package com.edu.domain.value;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import java.time.LocalDateTime;

@Embeddable
@NoArgsConstructor
@Getter
public class LectureDuration {

    private LocalDateTime startDate;
    private LocalDateTime endDate;
}
