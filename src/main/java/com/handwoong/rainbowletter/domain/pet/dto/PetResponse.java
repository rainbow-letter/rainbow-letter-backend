package com.handwoong.rainbowletter.domain.pet.dto;

import com.handwoong.rainbowletter.domain.favorite.dto.FavoriteResponse;
import com.handwoong.rainbowletter.domain.image.dto.ImageResponse;
import com.handwoong.rainbowletter.domain.pet.model.Pet;
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
                pet.getId(),
                pet.getName(),
                pet.getSpecies(),
                pet.getOwner(),
                pet.getPersonality(),
                pet.getDeathAnniversary(),
                ImageResponse.from(pet.getImage()),
                FavoriteResponse.from(pet.getFavorite())
        );
    }
}
