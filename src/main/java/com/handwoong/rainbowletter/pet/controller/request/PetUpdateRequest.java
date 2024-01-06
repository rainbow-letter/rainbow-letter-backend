package com.handwoong.rainbowletter.pet.controller.request;

import static com.handwoong.rainbowletter.common.util.validation.ValidateMessage.EMPTY_MESSAGE;
import static com.handwoong.rainbowletter.common.util.validation.ValidateMessage.PET_DEATH_ANNIVERSARY;
import static com.handwoong.rainbowletter.common.util.validation.ValidateMessage.PET_OWNER;
import static com.handwoong.rainbowletter.common.util.validation.ValidateMessage.PET_PERSONALITY;
import static com.handwoong.rainbowletter.common.util.validation.ValidateMessage.PET_PERSONALITY_SIZE;
import static com.handwoong.rainbowletter.common.util.validation.ValidateMessage.PET_SPECIES;
import static com.handwoong.rainbowletter.pet.domain.Personalities.MAX_PERSONALITY_LENGTH;
import static com.handwoong.rainbowletter.pet.domain.Personalities.MAX_PERSONALITY_SIZE;

import com.handwoong.rainbowletter.pet.domain.Personalities;
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

        @Size(max = MAX_PERSONALITY_SIZE, message = PET_PERSONALITY_SIZE)
        Set<@NotBlank(message = EMPTY_MESSAGE) @Size(max = MAX_PERSONALITY_LENGTH, message = PET_PERSONALITY) String> personalities,

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
                .personalities(Personalities.from(personalities))
                .deathAnniversary(deathAnniversary)
                .image(image)
                .build();
    }
}
