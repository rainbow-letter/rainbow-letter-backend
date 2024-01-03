package com.handwoong.rainbowletter.pet.controller.request;

import static com.handwoong.rainbowletter.common.util.validation.ValidateMessage.EMPTY_MESSAGE;
import static com.handwoong.rainbowletter.common.util.validation.ValidateMessage.PET_DEATH_ANNIVERSARY;
import static com.handwoong.rainbowletter.common.util.validation.ValidateMessage.PET_OWNER;
import static com.handwoong.rainbowletter.common.util.validation.ValidateMessage.PET_PERSONALITY;
import static com.handwoong.rainbowletter.common.util.validation.ValidateMessage.PET_PERSONALITY_SIZE;
import static com.handwoong.rainbowletter.common.util.validation.ValidateMessage.PET_SPECIES;

import com.handwoong.rainbowletter.pet.domain.dto.PetUpdate;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;
import java.util.Set;

public record PetUpdateRequest(
        @NotBlank(message = EMPTY_MESSAGE)
        @Size(max = 10, message = PET_SPECIES)
        String species,

        @NotBlank(message = EMPTY_MESSAGE)
        @Size(max = 10, message = PET_OWNER)
        String owner,

        @Size(max = 3, message = PET_PERSONALITY_SIZE)
        Set<@NotBlank(message = EMPTY_MESSAGE) @Size(max = 10, message = PET_PERSONALITY) String> personalities,

        @Nullable
        @Past(message = PET_DEATH_ANNIVERSARY)
        LocalDate deathAnniversary,

        @Nullable
        Long image
) {
    public PetUpdate toDto() {
        return PetUpdate.builder()
                .species(species)
                .owner(owner)
                .personalities(personalities)
                .deathAnniversary(deathAnniversary)
                .image(image)
                .build();
    }
}