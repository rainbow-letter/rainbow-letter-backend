package com.handwoong.rainbowletter.pet.service.port;

import com.handwoong.rainbowletter.member.domain.Email;
import com.handwoong.rainbowletter.pet.controller.response.PetResponse;
import com.handwoong.rainbowletter.pet.domain.Pet;
import java.util.List;

public interface PetRepository {
    Pet findByIdOrElseThrow(Long id);

    Pet findByEmailAndIdOrElseThrow(Email email, Long id);

    List<PetResponse> findAllByEmail(Email email);

    Pet findByEmailAndIdWithImageOrElseThrow(Email email, Long id);

    Pet save(Pet pet);

    void delete(Pet pet);
}
