package com.handwoong.rainbowletter.letter.controller.response;

import java.time.LocalDate;

import com.handwoong.rainbowletter.image.controller.response.ImageResponse;
import com.handwoong.rainbowletter.pet.domain.Pet;

import lombok.Builder;

@Builder
public record LetterPetResponse(
        Long id,
        String name,
        String owner,
        String species,
        String personalities,
        LocalDate deathAnniversary,
        ImageResponse image
) {
    public static LetterPetResponse from(final Pet pet) {
        return LetterPetResponse.builder()
                .id(pet.id())
                .name(pet.name())
                .owner(pet.owner())
                .species(pet.species())
                .personalities(pet.personalities())
                .deathAnniversary(pet.deathAnniversary())
                .image(ImageResponse.from(pet.image()))
                .build();
    }
}
