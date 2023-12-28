package com.handwoong.rainbowletter.pet.controller.response;

import com.handwoong.rainbowletter.favorite.controller.response.FavoriteResponse;
import com.handwoong.rainbowletter.image.controller.response.ImageResponse;
import com.handwoong.rainbowletter.pet.domain.Pet;
import java.time.LocalDate;
import java.util.Set;

public record PetResponse(
        Long id,
        String name,
        String species,
        String owner,
        Set<String> personality,
        LocalDate deathAnniversary,
        ImageResponse image,
        FavoriteResponse favorite
) {
    public static PetResponse from(final Pet pet) {
        return new PetResponse(
                pet.id(),
                pet.name(),
                pet.species(),
                pet.owner(),
                pet.personality(),
                pet.deathAnniversary(),
                ImageResponse.from(pet.image()),
                FavoriteResponse.from(pet.favorite())
        );
    }
}
