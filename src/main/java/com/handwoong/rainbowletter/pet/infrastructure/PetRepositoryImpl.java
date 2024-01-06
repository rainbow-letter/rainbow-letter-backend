package com.handwoong.rainbowletter.pet.infrastructure;

import static com.handwoong.rainbowletter.favorite.infrastructure.QFavoriteEntity.favoriteEntity;
import static com.handwoong.rainbowletter.image.infrastructure.QImageEntity.imageEntity;
import static com.handwoong.rainbowletter.member.infrastructure.QMemberEntity.memberEntity;
import static com.handwoong.rainbowletter.pet.infrastructure.QPetEntity.petEntity;

import com.handwoong.rainbowletter.favorite.controller.response.FavoriteResponse;
import com.handwoong.rainbowletter.image.controller.response.ImageResponse;
import com.handwoong.rainbowletter.member.domain.Email;
import com.handwoong.rainbowletter.pet.controller.response.PetResponse;
import com.handwoong.rainbowletter.pet.controller.response.PetResponseDto;
import com.handwoong.rainbowletter.pet.domain.Pet;
import com.handwoong.rainbowletter.pet.exception.PetResourceNotFoundException;
import com.handwoong.rainbowletter.pet.service.port.PetRepository;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class PetRepositoryImpl implements PetRepository {
    private final JPAQueryFactory queryFactory;
    private final PetJpaRepository petJpaRepository;

    @Override
    public Pet findByIdOrElseThrow(final Long id) {
        return Optional.ofNullable(
                        queryFactory.selectFrom(petEntity)
                                .distinct()
                                .leftJoin(petEntity.imageEntity)
                                .fetchJoin()
                                .innerJoin(petEntity.memberEntity, memberEntity)
                                .fetchJoin()
                                .innerJoin(petEntity.favoriteEntity, favoriteEntity)
                                .fetchJoin()
                                .where(petEntity.id.eq(id))
                                .fetchOne()
                )
                .orElseThrow(() -> new PetResourceNotFoundException(id))
                .toModel();
    }

    @Override
    public Pet findByEmailAndIdOrElseThrow(final Email email, final Long id) {
        return Optional.ofNullable(
                        queryFactory.selectFrom(petEntity)
                                .distinct()
                                .leftJoin(petEntity.imageEntity)
                                .fetchJoin()
                                .innerJoin(petEntity.memberEntity, memberEntity)
                                .fetchJoin()
                                .innerJoin(petEntity.favoriteEntity, favoriteEntity)
                                .fetchJoin()
                                .where(petEntity.id.eq(id).and(petEntity.memberEntity.email.eq(email.toString())))
                                .fetchOne()
                )
                .orElseThrow(() -> new PetResourceNotFoundException(id))
                .toModel();
    }

    @Override
    public List<PetResponse> findAllByEmail(final Email email) {
        return queryFactory.select(Projections.constructor(
                        PetResponseDto.class,
                        petEntity.id,
                        petEntity.name,
                        petEntity.species,
                        petEntity.owner,
                        petEntity.personalities,
                        petEntity.deathAnniversary,
                        Projections.constructor(
                                ImageResponse.class,
                                petEntity.imageEntity.id,
                                petEntity.imageEntity.objectKey,
                                petEntity.imageEntity.url
                        ),
                        Projections.constructor(
                                FavoriteResponse.class,
                                petEntity.favoriteEntity.id,
                                petEntity.favoriteEntity.total,
                                petEntity.favoriteEntity.dayIncreaseCount,
                                petEntity.favoriteEntity.canIncrease
                        )
                ))
                .distinct()
                .from(petEntity)
                .innerJoin(petEntity.favoriteEntity, favoriteEntity)
                .leftJoin(petEntity.imageEntity)
                .where(petEntity.memberEntity.email.eq(email.toString()))
                .fetch()
                .stream()
                .map(PetResponse::from)
                .toList();
    }

    @Override
    public Pet findByEmailAndIdWithImageOrElseThrow(final Email email, final Long id) {
        return Optional.ofNullable(
                        queryFactory.selectFrom(petEntity)
                                .innerJoin(petEntity.imageEntity, imageEntity)
                                .fetchJoin()
                                .innerJoin(petEntity.memberEntity, memberEntity)
                                .fetchJoin()
                                .innerJoin(petEntity.favoriteEntity, favoriteEntity)
                                .fetchJoin()
                                .where(petEntity.id.eq(id).and(petEntity.memberEntity.email.eq(email.toString())))
                                .fetchOne()
                )
                .orElseThrow(() -> new PetResourceNotFoundException(id))
                .toModel();
    }

    @Override
    public Pet save(final Pet pet) {
        return petJpaRepository.save(PetEntity.from(pet)).toModel();
    }

    @Override
    public void delete(final Pet pet) {
        petJpaRepository.delete(PetEntity.from(pet));
    }
}
