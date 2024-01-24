package com.handwoong.rainbowletter.letter.controller.response;

import com.handwoong.rainbowletter.image.controller.response.ImageResponse;
import com.handwoong.rainbowletter.pet.domain.Pet;
import lombok.Builder;

@Builder
public record LetterPetResponse(Long id, String name, ImageResponse image) {
    public static LetterPetResponse from(final Pet pet) {
        return LetterPetResponse.builder()
                .id(pet.id())
                .name(pet.name())
                .image(ImageResponse.from(pet.image()))
                .build();
    }
}
