package com.handwoong.rainbowletter.pet.controller.response;

import com.handwoong.rainbowletter.pet.domain.Pet;
import java.util.List;

public record PetResponses(List<PetResponse> petResponses) {
    public static PetResponses from(final List<Pet> pets) {
        final List<PetResponse> petResponses = pets.stream()
                .map(PetResponse::from)
                .toList();
        return new PetResponses(petResponses);
    }
}
