package com.handwoong.rainbowletter.pet.controller.response;

import com.handwoong.rainbowletter.favorite.controller.response.FavoriteResponse;
import com.handwoong.rainbowletter.image.controller.response.ImageResponse;
import com.handwoong.rainbowletter.pet.domain.Pet;
import java.time.LocalDate;
import lombok.Builder;

@Builder
public record PetResponseDto(
        Long id,
        String name,
        String species,
        String owner,
        String personalities,
        LocalDate deathAnniversary,
        ImageResponse image,
        FavoriteResponse favorite
) {
    public static PetResponseDto from(final Pet pet) {
        return PetResponseDto.builder()
                .id(pet.id())
                .name(pet.name())
                .species(pet.species())
                .owner(pet.owner())
                .personalities(pet.personalities())
                .deathAnniversary(pet.deathAnniversary())
                .image(ImageResponse.from(pet.image()))
                .favorite(FavoriteResponse.from(pet.favorite()))
                .build();
    }
}
