package com.handwoong.rainbowletter.pet.infrastructure;

import com.handwoong.rainbowletter.member.domain.Email;
import com.handwoong.rainbowletter.pet.domain.Pet;
import com.handwoong.rainbowletter.pet.exception.PetResourceNotFoundException;
import com.handwoong.rainbowletter.pet.service.port.PetRepository;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class PetRepositoryImpl implements PetRepository {
    private final PetJpaRepository petJpaRepository;

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
    public Optional<Pet> findByEmailAndIdWithImage(final Email email, final Long id) {
        return petJpaRepository.findOneByIdWithImage(email.toString(), id).map(PetEntity::toModel);
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
