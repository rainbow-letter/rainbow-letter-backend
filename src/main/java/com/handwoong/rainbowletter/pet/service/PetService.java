package com.handwoong.rainbowletter.pet.service;

import com.handwoong.rainbowletter.member.domain.Email;
import com.handwoong.rainbowletter.pet.domain.Pet;
import com.handwoong.rainbowletter.pet.domain.dto.PetCreate;
import com.handwoong.rainbowletter.pet.domain.dto.PetUpdate;
import java.util.List;

public interface PetService {
    Pet findByIdOrElseThrow(Email email, Long id);

    Pet findById(String email, Long id);

    List<Pet> findAll(String email);

    void create(String email, PetCreate request);

    void update(String email, Long id, PetUpdate request);

    void deleteImage(String email, Long id);

    void delete(String email, Long id);
}
