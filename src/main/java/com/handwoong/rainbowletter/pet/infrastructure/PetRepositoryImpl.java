package com.handwoong.rainbowletter.pet.infrastructure;

import static com.handwoong.rainbowletter.favorite.infrastructure.QFavoriteEntity.favoriteEntity;
import static com.handwoong.rainbowletter.image.infrastructure.QImageEntity.imageEntity;
import static com.handwoong.rainbowletter.member.infrastructure.QMemberEntity.memberEntity;
import static com.handwoong.rainbowletter.pet.infrastructure.QPetEntity.petEntity;

import com.handwoong.rainbowletter.member.domain.Email;
import com.handwoong.rainbowletter.pet.domain.Pet;
import com.handwoong.rainbowletter.pet.exception.PetResourceNotFoundException;
import com.handwoong.rainbowletter.pet.service.port.PetRepository;
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
        return petJpaRepository.findById(id)
                .orElseThrow(() -> new PetResourceNotFoundException(id))
                .toModel();
    }

    @Override
    public Pet findByEmailAndIdOrElseThrow(final Email email, final Long id) {
        return findByEmailAndId(email, id)
                .orElseThrow(() -> new PetResourceNotFoundException(id));
    }

    @Override
    public Optional<Pet> findByEmailAndId(final Email email, final Long id) {
        return petJpaRepository.findOneById(email.toString(), id).map(PetEntity::toModel);
    }

    @Override
    public List<Pet> findAllByEmail(final Email email) {
        return petJpaRepository.findAll(email.toString())
                .stream()
                .map(PetEntity::toModel)
                .toList();
    }

    @Override
    public Pet findByEmailAndIdWithImageOrElseThrow(final Email email, final Long id) {
        return Optional.ofNullable(
                        queryFactory.select(petEntity)
                                .from(petEntity)
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
