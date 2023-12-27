package com.handwoong.rainbowletter.pet.domain.dto;

import static com.handwoong.rainbowletter.common.util.ValidateMessage.EMPTY_MESSAGE;
import static com.handwoong.rainbowletter.common.util.ValidateMessage.PET_DEATH_ANNIVERSARY;
import static com.handwoong.rainbowletter.common.util.ValidateMessage.PET_NAME;
import static com.handwoong.rainbowletter.common.util.ValidateMessage.PET_OWNER;
import static com.handwoong.rainbowletter.common.util.ValidateMessage.PET_PERSONALITY_SIZE;
import static com.handwoong.rainbowletter.common.util.ValidateMessage.PET_SPECIES;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;
import java.util.Set;

public record PetRequest(
        @NotBlank(message = EMPTY_MESSAGE)
        @Size(max = 20, message = PET_NAME)
        String name,

        @NotBlank(message = EMPTY_MESSAGE)
        @Size(max = 10, message = PET_SPECIES)
        String species,

        @NotBlank(message = EMPTY_MESSAGE)
        @Size(max = 10, message = PET_OWNER)
        String owner,

        @Size(max = 3, message = PET_PERSONALITY_SIZE)
        Set<@NotBlank(message = EMPTY_MESSAGE) @Size(max = 10, message = PET_OWNER) String> personality,

        @Past(message = PET_DEATH_ANNIVERSARY)
        LocalDate deathAnniversary,

        @Nullable
        Long image
) {
}
