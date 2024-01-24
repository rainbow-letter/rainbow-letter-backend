package com.handwoong.rainbowletter.pet.domain.dto;

import com.handwoong.rainbowletter.pet.domain.Personalities;
import jakarta.annotation.Nullable;
import java.time.LocalDate;
import lombok.Builder;

@Builder
public record PetUpdate(
        String species,
        String owner,
        Personalities personalities,
        @Nullable LocalDate deathAnniversary,
        @Nullable Long image
) {
}
