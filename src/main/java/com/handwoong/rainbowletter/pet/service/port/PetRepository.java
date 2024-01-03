package com.handwoong.rainbowletter.pet.service.port;

import com.handwoong.rainbowletter.member.domain.Email;
import com.handwoong.rainbowletter.pet.domain.Pet;
import java.util.List;
import java.util.Optional;

public interface PetRepository {
    Optional<Pet> findByEmailAndId(Email email, Long id);

    List<Pet> findAllByEmail(Email email);

    Optional<Pet> findByEmailAndIdWithImage(Email email, Long id);

    Pet save(Pet pet);

    void delete(Pet pet);
}
