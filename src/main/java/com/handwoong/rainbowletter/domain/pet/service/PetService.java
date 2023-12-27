package com.handwoong.rainbowletter.domain.pet.service;

import com.handwoong.rainbowletter.domain.pet.dto.PetRequest;
import com.handwoong.rainbowletter.domain.pet.dto.PetResponse;
import com.handwoong.rainbowletter.domain.pet.dto.PetResponses;

public interface PetService {
    void create(final String email, final PetRequest request);

    PetResponses findAll(final String email);

    PetResponse findById(final String email, final Long petId);

    void edit(final String email, final Long petId, final PetRequest request);

    void deleteImage(final String email, final Long petId);

    void delete(final String email, final Long petId);
}
