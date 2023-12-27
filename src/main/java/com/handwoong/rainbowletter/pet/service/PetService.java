package com.handwoong.rainbowletter.pet.service;

import com.handwoong.rainbowletter.pet.controller.response.PetListResponse;
import com.handwoong.rainbowletter.pet.controller.response.PetResponse;
import com.handwoong.rainbowletter.pet.domain.dto.PetRequest;

public interface PetService {
    void create(final String email, final PetRequest request);

    PetListResponse findAll(final String email);

    PetResponse findById(final String email, final Long petId);

    void edit(final String email, final Long petId, final PetRequest request);

    void deleteImage(final String email, final Long petId);

    void delete(final String email, final Long petId);
}
