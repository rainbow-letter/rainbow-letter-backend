package com.handwoong.rainbowletter.pet.infrastructure;

import com.handwoong.rainbowletter.member.domain.Email;
import com.handwoong.rainbowletter.pet.domain.Pet;
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
    public Optional<Pet> findByEmailAndId(final Email email, final Long id) {
        return petJpaRepository.findOneById(email.toString(), id);
    }

    @Override
    public List<Pet> findAllByEmail(final Email email) {
        return petJpaRepository.findAll(email.toString());
    }

    @Override
    public Optional<Pet> findByEmailAndIdWithImage(final Email email, final Long id) {
        return petJpaRepository.findOneByIdWithImage(email.toString(), id);
    }

    @Override
    public Pet save(final Pet pet) {
        return petJpaRepository.save(PetEntity.fromModel(pet)).toModel();
    }

    @Override
    public void delete(final Pet pet) {
        save(pet);
        petJpaRepository.delete(PetEntity.fromModel(pet));
    }
}
