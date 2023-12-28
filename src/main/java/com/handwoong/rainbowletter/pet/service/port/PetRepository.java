package com.handwoong.rainbowletter.pet.service.port;

import com.handwoong.rainbowletter.member.domain.Email;
import com.handwoong.rainbowletter.pet.domain.Pet;
import java.util.List;
import java.util.Optional;

public interface PetRepository {
    Optional<Pet> findById(Email email, Long id);

    List<Pet> findAll(Email email);

    Optional<Pet> findByIdWithImage(Email email, Long id);

    void save(Pet pet);

    void delete(Pet pet);
}
