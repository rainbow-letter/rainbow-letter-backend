package com.handwoong.rainbowletter.pet.service;

import com.handwoong.rainbowletter.member.domain.Email;
import com.handwoong.rainbowletter.pet.domain.Pet;
import com.handwoong.rainbowletter.pet.domain.dto.PetCreate;
import com.handwoong.rainbowletter.pet.domain.dto.PetUpdate;
import java.util.List;

public interface PetService {
    Pet findByEmailAndIdOrElseThrow(Email email, Long id);

    Pet findByEmailAndId(Email email, Long id);

    List<Pet> findAllByEmail(Email email);

    Pet create(Email email, PetCreate request);

    Pet update(Email email, Long id, PetUpdate request);

    Pet deleteImage(Email email, Long id);

    void delete(Email email, Long id);
}
