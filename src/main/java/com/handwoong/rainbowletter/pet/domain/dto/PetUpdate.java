package com.handwoong.rainbowletter.pet.domain.dto;

import jakarta.annotation.Nullable;
import java.time.LocalDate;
import java.util.Set;
import lombok.Builder;

@Builder
public record PetUpdate(
        String species,
        String owner,
        Set<String> personalities,
        @Nullable LocalDate deathAnniversary,
        @Nullable Long image
) {
}
