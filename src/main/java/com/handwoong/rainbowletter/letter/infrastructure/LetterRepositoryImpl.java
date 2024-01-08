package com.handwoong.rainbowletter.letter.infrastructure;

import static com.handwoong.rainbowletter.letter.infrastructure.QLetterEntity.letterEntity;
import static com.handwoong.rainbowletter.pet.infrastructure.QPetEntity.petEntity;

import com.handwoong.rainbowletter.image.controller.response.ImageResponse;
import com.handwoong.rainbowletter.letter.controller.response.LetterBoxResponse;
import com.handwoong.rainbowletter.letter.controller.response.LetterPetResponse;
import com.handwoong.rainbowletter.letter.controller.response.LetterResponse;
import com.handwoong.rainbowletter.letter.domain.Letter;
import com.handwoong.rainbowletter.letter.exception.LetterResourceNotFoundException;
import com.handwoong.rainbowletter.letter.service.port.LetterRepository;
import com.handwoong.rainbowletter.member.domain.Email;
import com.handwoong.rainbowletter.pet.infrastructure.QPetEntity;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import java.util.Optional;
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
        return queryFactory.select(Projections.constructor(
                        LetterBoxResponse.class,
                        letter.id,
                        letter.summary,
                        letter.status,
                        letter.petEntity.name.as("petName"),
                        letter.createdAt
                ))
                .from(letter)
                .innerJoin(letter.petEntity, petEntity)
                .where(letter.petEntity.memberEntity.email.eq(email.toString()))
                .orderBy(letter.createdAt.desc())
                .fetch();
    }

    @Override
    public LetterResponse findLetterByIdOrElseThrow(final Long id) {
        final QLetterEntity letter = letterEntity;
        final QPetEntity pet = petEntity;
        final LetterResponse result = queryFactory.select(Projections.constructor(
                        LetterResponse.class,
                        letter.id,
                        letter.summary,
                        letter.content,
                        Projections.constructor(
                                LetterPetResponse.class,
                                pet.id,
                                pet.name,
                                Projections.constructor(
                                        ImageResponse.class,
                                        pet.imageEntity.id,
                                        pet.imageEntity.objectKey,
                                        pet.imageEntity.url
                                )
                        ),
                        Projections.constructor(
                                ImageResponse.class,
                                letter.imageEntity.id,
                                letter.imageEntity.objectKey,
                                letter.imageEntity.url
                        ),
                        letter.createdAt
                ))
                .distinct()
                .from(letter)
                .innerJoin(letter.petEntity, pet)
                .leftJoin(pet.imageEntity)
                .leftJoin(letter.imageEntity)
                .where(letter.id.eq(id))
                .fetchOne();
        return Optional.ofNullable(result)
                .orElseThrow(() -> new LetterResourceNotFoundException(id));
    }

    @Override
    public boolean existsByPet(final Long petId) {
        final List<LetterEntity> result = queryFactory.selectFrom(letterEntity)
                .innerJoin(letterEntity.petEntity, petEntity)
                .where(letterEntity.petEntity.id.eq(petId))
                .fetch();
        return result.size() > 1;
    }
}
