package com.handwoong.rainbowletter.letter.infrastructure;

import static com.handwoong.rainbowletter.letter.infrastructure.QLetterEntity.letterEntity;

import com.handwoong.rainbowletter.letter.controller.response.LetterBoxResponse;
import com.handwoong.rainbowletter.letter.domain.Letter;
import com.handwoong.rainbowletter.letter.service.port.LetterRepository;
import com.handwoong.rainbowletter.member.domain.Email;
import com.handwoong.rainbowletter.pet.infrastructure.QPetEntity;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class LetterRepositoryImpl implements LetterRepository {
    private final JPAQueryFactory queryFactory;
    private final LetterJpaRepository letterJpaRepository;

    @Override
    public Letter save(final Letter letter) {
        return letterJpaRepository.save(LetterEntity.from(letter)).toModel();
    }

    @Override
    public List<LetterBoxResponse> findAllLetterBoxByEmail(final Email email) {
        final QLetterEntity letter = letterEntity;
        final QPetEntity pet = QPetEntity.petEntity;
        return queryFactory.select(Projections.constructor(
                        LetterBoxResponse.class,
                        letter.id,
                        letter.summary,
                        letter.status,
                        letter.petEntity.name.as("petName"),
                        letter.createdAt
                )).from(letter)
                .innerJoin(letter.petEntity, pet)
                .where(letter.petEntity.memberEntity.email.eq(email.toString()))
                .orderBy(letter.createdAt.desc())
                .fetch();
    }
}
