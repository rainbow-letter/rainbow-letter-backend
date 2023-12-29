package com.handwoong.rainbowletter.pet.controller.response;

import com.handwoong.rainbowletter.pet.domain.Pet;
import java.util.List;
import lombok.Builder;

@Builder
public record PetResponses(List<PetResponse> pets) {
    public static PetResponses from(final List<Pet> pets) {
        final List<PetResponse> petResponses = pets.stream()
                .map(PetResponse::from)
                .toList();
        return PetResponses.builder()
                .pets(petResponses)
                .build();
    }
}
