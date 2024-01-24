package com.handwoong.rainbowletter.pet.controller.response;

import com.handwoong.rainbowletter.favorite.controller.response.FavoriteResponse;
import com.handwoong.rainbowletter.image.controller.response.ImageResponse;
import com.handwoong.rainbowletter.pet.domain.Personalities;
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
        Set<String> personalities,
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
                .personalities(Personalities.from(pet.personalities()).personalities())
                .deathAnniversary(pet.deathAnniversary())
                .image(ImageResponse.from(pet.image()))
                .favorite(FavoriteResponse.from(pet.favorite()))
                .build();
    }

    public static PetResponse from(final PetResponseDto petResponseDto) {
        return PetResponse.builder()
                .id(petResponseDto.id())
                .name(petResponseDto.name())
                .species(petResponseDto.species())
                .owner(petResponseDto.owner())
                .personalities(Personalities.from(petResponseDto.personalities()).personalities())
                .deathAnniversary(petResponseDto.deathAnniversary())
                .image(petResponseDto.image())
                .favorite(petResponseDto.favorite())
                .build();
    }
}
