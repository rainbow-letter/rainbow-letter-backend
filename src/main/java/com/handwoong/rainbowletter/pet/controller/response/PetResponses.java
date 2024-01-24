package com.handwoong.rainbowletter.pet.controller.response;

import java.util.List;
import lombok.Builder;

@Builder
public record PetResponses(List<PetResponse> pets) {
    public static PetResponses from(final List<PetResponse> petResponses) {
        return PetResponses.builder()
                .pets(petResponses)
                .build();
    }
}
