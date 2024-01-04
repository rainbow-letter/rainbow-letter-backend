package com.handwoong.rainbowletter.letter.domain;

import com.handwoong.rainbowletter.image.domain.Image;
import com.handwoong.rainbowletter.pet.domain.Pet;
import jakarta.annotation.Nullable;
import lombok.Builder;

@Builder
public record Letter(
        Long id,
        Summary summary,
        Content content,
        LetterStatus status,
        Pet pet,
        @Nullable
        Image image,
        @Nullable
        Reply reply
) {
}
