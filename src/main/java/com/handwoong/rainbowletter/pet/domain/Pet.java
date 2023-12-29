package com.handwoong.rainbowletter.pet.domain;

import com.handwoong.rainbowletter.favorite.domain.Favorite;
import com.handwoong.rainbowletter.image.domain.Image;
import com.handwoong.rainbowletter.member.domain.Member;
import com.handwoong.rainbowletter.pet.domain.dto.PetCreate;
import com.handwoong.rainbowletter.pet.domain.dto.PetUpdate;
import jakarta.annotation.Nullable;
import java.time.LocalDate;
import java.util.Objects;
import java.util.Set;
import lombok.Builder;

@Builder
public record Pet(
        Long id,
        String name,
        String species,
        String owner,
        Set<String> personality,
        @Nullable LocalDate deathAnniversary,
        @Nullable Image image,
        Member member,
        Favorite favorite
) {
    public static Pet create(final Member member, final Image image, final Favorite favorite, final PetCreate request) {
        return Pet.builder()
                .name(request.name())
                .species(request.species())
                .owner(request.owner())
                .personality(request.personality())
                .deathAnniversary(request.deathAnniversary())
                .image(image)
                .member(member)
                .favorite(favorite)
                .build();
    }

    public Pet update(final PetUpdate request, final Image image) {
        return Pet.builder()
                .id(id)
                .name(name)
                .species(request.species())
                .owner(request.owner())
                .personality(request.personality())
                .deathAnniversary(request.deathAnniversary())
                .image(Objects.nonNull(image) ? image : this.image)
                .member(member)
                .favorite(favorite)
                .build();
    }

    public Pet removeImage() {
        return Pet.builder()
                .id(id)
                .name(name)
                .species(species)
                .owner(owner)
                .personality(personality)
                .deathAnniversary(deathAnniversary)
                .favorite(favorite)
                .member(member)
                .image(null)
                .build();
    }

    public Pet clean() {
        return Pet.builder()
                .id(id)
                .name(name)
                .species(species)
                .owner(owner)
                .personality(personality)
                .deathAnniversary(deathAnniversary)
                .favorite(favorite)
                .member(null)
                .image(null)
                .build();
    }
}
