package com.edu.domain.value;

import lombok.Builder;
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

    @Builder
    public LectureDuration(LocalDateTime startDate, LocalDateTime endDate) {
        this.startDate = startDate;
        this.endDate = endDate;
    }
}
