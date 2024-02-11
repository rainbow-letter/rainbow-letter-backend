package com.handwoong.rainbowletter.letter.controller.response;

import com.handwoong.rainbowletter.image.controller.response.ImageResponse;
import com.handwoong.rainbowletter.pet.domain.Pet;
import lombok.Builder;

@Builder
public record LetterPetResponse(Long id, String name, String species, String personalities, ImageResponse image) {
    public static LetterPetResponse from(final Pet pet) {
        return LetterPetResponse.builder()
                .id(pet.id())
                .name(pet.name())
                .species(pet.species())
                .personalities(pet.personalities())
                .image(ImageResponse.from(pet.image()))
                .build();
    }
}
