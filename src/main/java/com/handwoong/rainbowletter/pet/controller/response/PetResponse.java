package com.handwoong.rainbowletter.pet.controller.response;

import com.handwoong.rainbowletter.favorite.controller.response.FavoriteResponse;
import com.handwoong.rainbowletter.image.controller.response.ImageResponse;
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
    public static PetResponse from(final com.handwoong.rainbowletter.pet.infrastructure.Pet pet) {
        return new PetResponse(
                pet.getId(),
                pet.getName(),
                pet.getSpecies(),
                pet.getOwner(),
                pet.getPersonality(),
                pet.getDeathAnniversary(),
                ImageResponse.from(pet.getImageEntity()),
                FavoriteResponse.from(pet.getFavorite())
        );
    }
}
