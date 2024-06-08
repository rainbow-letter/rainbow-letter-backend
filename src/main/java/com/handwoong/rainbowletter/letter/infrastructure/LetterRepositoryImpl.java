package com.handwoong.rainbowletter.letter.infrastructure;

import static com.handwoong.rainbowletter.letter.infrastructure.QLetterEntity.letterEntity;
import static com.handwoong.rainbowletter.letter.infrastructure.QReplyEntity.replyEntity;
import static com.handwoong.rainbowletter.pet.infrastructure.QPetEntity.petEntity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import com.handwoong.rainbowletter.image.controller.response.ImageResponse;
import com.handwoong.rainbowletter.letter.controller.request.ReplyTypeRequest;
import com.handwoong.rainbowletter.letter.controller.response.LetterAdminResponse;
import com.handwoong.rainbowletter.letter.controller.response.LetterBoxResponse;
import com.handwoong.rainbowletter.letter.controller.response.LetterPetResponse;
import com.handwoong.rainbowletter.letter.controller.response.LetterResponse;
import com.handwoong.rainbowletter.letter.controller.response.ReplyAdminResponse;
import com.handwoong.rainbowletter.letter.controller.response.ReplyResponse;
import com.handwoong.rainbowletter.letter.domain.Letter;
import com.handwoong.rainbowletter.letter.domain.ReplyType;
import com.handwoong.rainbowletter.letter.exception.LetterResourceNotFoundException;
import com.handwoong.rainbowletter.letter.exception.LetterShareLinkNotFoundException;
import com.handwoong.rainbowletter.letter.service.port.LetterRepository;
import com.handwoong.rainbowletter.member.domain.Email;
import com.handwoong.rainbowletter.pet.infrastructure.QPetEntity;
import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.NumberExpression;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

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
    public Letter findByIdOrElseThrow(final Long id) {
        return Optional.ofNullable(
                        queryFactory.selectFrom(letterEntity)
                                .distinct()
                                .leftJoin(letterEntity.imageEntity)
                                .fetchJoin()
                                .leftJoin(letterEntity.replyEntity, replyEntity)
                                .fetchJoin()
                                .innerJoin(letterEntity.petEntity, petEntity)
                                .fetchJoin()
                                .where(letterEntity.id.eq(id))
                                .fetchOne()
                )
                .orElseThrow(() -> new LetterResourceNotFoundException(id))
                .toModel();
    }

    @Override
    public Letter findByReplyIdOrElseThrow(final Long replyId) {
        return Optional.ofNullable(
                        queryFactory.selectFrom(letterEntity)
                                .distinct()
                                .leftJoin(letterEntity.imageEntity)
                                .fetchJoin()
                                .leftJoin(letterEntity.replyEntity, replyEntity)
                                .fetchJoin()
                                .innerJoin(letterEntity.petEntity, petEntity)
                                .fetchJoin()
                                .where(letterEntity.replyEntity.id.eq(replyId))
                                .fetchOne()
                )
                .orElseThrow(() -> new LetterResourceNotFoundException(replyId))
                .toModel();
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
                        letter.replyEntity.readStatus,
                        letter.createdAt
                ))
                .distinct()
                .from(letter)
                .leftJoin(letter.replyEntity, replyEntity)
                .innerJoin(letter.petEntity, petEntity)
                .where(letter.petEntity.memberEntity.email.eq(email.toString()))
                .orderBy(letter.createdAt.desc())
                .fetch();
    }

    @Override
    public LetterResponse findLetterResponseByIdOrElseThrow(final Email email, final Long id) {
        final QLetterEntity letter = letterEntity;
        final QPetEntity pet = petEntity;
        final QReplyEntity reply = replyEntity;
        final LetterResponse result = selectLetterResponse()
                .distinct()
                .from(letter)
                .innerJoin(letter.petEntity, pet)
                .leftJoin(pet.imageEntity)
                .leftJoin(letter.imageEntity)
                .leftJoin(letter.replyEntity, reply).on(reply.type.eq(ReplyType.REPLY))
                .where(letter.id.eq(id).and(letter.petEntity.memberEntity.email.eq(email.toString())))
                .fetchOne();
        return Optional.ofNullable(result)
                .orElseThrow(() -> new LetterResourceNotFoundException(id));
    }

    @Override
    public LetterResponse findLetterResponseByShareLinkOrElseThrow(final String shareLink) {
        final QLetterEntity letter = letterEntity;
        final QPetEntity pet = petEntity;
        final QReplyEntity reply = replyEntity;
        final LetterResponse result = selectLetterResponse()
                .distinct()
                .from(letter)
                .innerJoin(letter.petEntity, pet)
                .leftJoin(pet.imageEntity)
                .leftJoin(letter.imageEntity)
                .leftJoin(letter.replyEntity, reply).on(reply.type.eq(ReplyType.REPLY))
                .where(letter.shareLink.eq(shareLink))
                .fetchOne();
        return Optional.ofNullable(result)
                .orElseThrow(() -> new LetterShareLinkNotFoundException(shareLink));
    }

    @Override
    public Page<LetterAdminResponse> findAdminAllLetterResponses(final ReplyTypeRequest type,
                                                                 final LocalDate startDate,
                                                                 final LocalDate endDate,
                                                                 final String email,
                                                                 final Pageable pageable) {
        final QLetterEntity letter = letterEntity;
        final QPetEntity pet = petEntity;
        final QReplyEntity reply = replyEntity;

        final JPQLQuery<Long> letterCount = JPAExpressions.select(letter.count())
                .from(letter)
                .where(letter.petEntity.memberEntity.id.eq(pet.memberEntity.id));
        final NumberExpression<Long> letterCountExpression = Expressions.asNumber(
                ExpressionUtils.as(letterCount, Expressions.numberPath(Long.class, "count")));
        final List<LetterAdminResponse> result = queryFactory.select(Projections.constructor(
                        LetterAdminResponse.class,
                        letter.id,
                        pet.memberEntity.id.as("memberId"),
                        pet.memberEntity.email.as("email"),
                        pet.memberEntity.phoneNumber.as("phoneNumber"),
                        pet.memberEntity.provider.as("provider"),
                        pet.memberEntity.createdAt.as("memberCreatedAt"),
                        letter.summary,
                        letter.content,
                        letter.shareLink,
                        letterCountExpression,
                        Projections.constructor(
                                LetterPetResponse.class,
                                pet.id,
                                pet.name,
                                pet.owner,
                                pet.species,
                                pet.personalities,
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
                        Projections.constructor(
                                ReplyAdminResponse.class,
                                reply.id,
                                reply.summary,
                                reply.content,
                                reply.inspection,
                                reply.inspectionTime,
                                reply.readStatus,
                                reply.type,
                                reply.timestamp,
                                reply.chatGptEntity.content
                        ),
                        letter.createdAt
                ))
                .distinct()
                .from(letter)
                .where(emailFilter(email))
                .innerJoin(letter.petEntity, pet)
                .leftJoin(pet.imageEntity)
                .leftJoin(letter.imageEntity)
                .leftJoin(letter.replyEntity, reply)
                .leftJoin(reply.chatGptEntity)
                .where(dateFilter(startDate, endDate), typeFilter(type))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(letterEntity.createdAt.desc())
                .fetch();

        final JPAQuery<Long> countLetters = queryFactory
                .select(letterEntity.count())
                .from(letterEntity)
                .innerJoin(letterEntity.petEntity, petEntity)
                .where(dateFilter(startDate, endDate), typeFilter(type));
        return PageableExecutionUtils.getPage(result, pageable, countLetters::fetchOne);
    }

    private BooleanExpression emailFilter(final String email) {
        if (StringUtils.hasText(email)) {
            return petEntity.memberEntity.email.containsIgnoreCase(email);
        }
        return null;
    }

    private BooleanExpression dateFilter(final LocalDate startDate, final LocalDate endDate) {
        BooleanExpression isGoeStartDate = letterEntity.createdAt
                .goe(LocalDateTime.of(startDate, LocalTime.MIN));
        BooleanExpression isLoeEndDate = letterEntity.createdAt
                .loe(LocalDateTime.of(endDate, LocalTime.MAX).withNano(0));
        return Expressions.allOf(isGoeStartDate, isLoeEndDate);
    }

    private BooleanExpression typeFilter(final ReplyTypeRequest type) {
        if (Objects.isNull(type.getReply())) {
            return null;
        }
        return letterEntity.replyEntity.type.eq(type.getReply());
    }

    private JPAQuery<LetterResponse> selectLetterResponse() {
        final QLetterEntity letter = letterEntity;
        final QPetEntity pet = petEntity;
        final QReplyEntity reply = replyEntity;
        return queryFactory.select(Projections.constructor(
                LetterResponse.class,
                letter.id,
                letter.summary,
                letter.content,
                letter.shareLink,
                Projections.constructor(
                        LetterPetResponse.class,
                        pet.id,
                        pet.name,
                        pet.owner,
                        pet.species,
                        pet.personalities,
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
                Projections.constructor(
                        ReplyResponse.class,
                        reply.id,
                        reply.summary,
                        reply.content,
                        reply.inspection,
                        reply.readStatus,
                        reply.type,
                        reply.timestamp
                ),
                letter.createdAt
        ));
    }

    @Override
    public boolean existsByPet(final Long petId) {
        final Integer result = queryFactory.selectOne()
                .from(letterEntity)
                .innerJoin(letterEntity.petEntity, petEntity)
                .where(letterEntity.petEntity.id.eq(petId))
                .fetchFirst();
        return result != null;
    }
}
