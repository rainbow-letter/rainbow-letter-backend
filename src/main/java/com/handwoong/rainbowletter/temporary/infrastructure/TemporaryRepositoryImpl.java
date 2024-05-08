package com.handwoong.rainbowletter.temporary.infrastructure;

import static com.handwoong.rainbowletter.temporary.infrastructure.QTemporaryEntity.temporaryEntity;

import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.handwoong.rainbowletter.temporary.domain.Temporary;
import com.handwoong.rainbowletter.temporary.domain.TemporaryStatus;
import com.handwoong.rainbowletter.temporary.service.port.TemporaryRepository;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class TemporaryRepositoryImpl implements TemporaryRepository {

    private final JPAQueryFactory queryFactory;
    private final TemporaryJpaRepository temporaryJpaRepository;

    @Override
    public Temporary save(final Temporary temporary) {
        return temporaryJpaRepository.save(TemporaryEntity.from(temporary)).toModel();
    }

    @Override
    public Temporary findByIdAndMemberIdOrElseThrow(final Long id, final Long memberId) {
        return Optional.ofNullable(
                        queryFactory.selectFrom(temporaryEntity)
                                .where(
                                        temporaryEntity.id.eq(id),
                                        temporaryEntity.memberId.eq(memberId),
                                        temporaryEntity.status.eq(TemporaryStatus.SAVE)
                                )
                                .fetchOne())
                .orElseThrow(() -> new IllegalArgumentException("임시저장 편지를 찾을 수 없습니다."))
                .toModel();
    }

    @Override
    public boolean existsByMemberIdAndPetId(final Long memberId, final Long petId) {
        return temporaryJpaRepository.existsByMemberIdAndPetId(memberId, petId);
    }

    @Override
    public Temporary findByMemberIdAndPetId(final Long memberId, final Long petId) {
        return Optional.ofNullable(queryFactory.selectFrom(temporaryEntity)
                        .where(
                                temporaryEntity.memberId.eq(memberId),
                                temporaryEntity.petId.eq(petId),
                                temporaryEntity.status.eq(TemporaryStatus.SAVE)
                        )
                        .fetchOne())
                .orElseThrow(() -> new IllegalArgumentException("임시저장 편지를 찾을 수 없습니다."))
                .toModel();
    }
}
