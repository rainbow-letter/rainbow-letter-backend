package com.handwoong.rainbowletter.pet.controller.response;

import com.handwoong.rainbowletter.favorite.controller.response.FavoriteResponse;
import com.handwoong.rainbowletter.image.controller.response.ImageResponse;
import com.handwoong.rainbowletter.pet.domain.Pet;
import java.time.LocalDate;
import java.util.Set;
import lombok.Builder;

@Builder
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
        return PetResponse.builder()
                .id(pet.id())
                .name(pet.name())
                .species(pet.species())
                .owner(pet.owner())
                .personality(pet.personality())
                .deathAnniversary(pet.deathAnniversary())
                .image(ImageResponse.from(pet.image()))
                .favorite(FavoriteResponse.from(pet.favorite()))
                .build();
    }
}
