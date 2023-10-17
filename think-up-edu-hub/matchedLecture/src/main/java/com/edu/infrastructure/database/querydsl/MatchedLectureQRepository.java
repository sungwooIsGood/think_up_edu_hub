package com.edu.infrastructure.database.querydsl;

import com.edu.domain.repository.MatchedLectureRepository;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class MatchedLectureQRepository implements MatchedLectureRepository {

    private final JPAQueryFactory query;
}
