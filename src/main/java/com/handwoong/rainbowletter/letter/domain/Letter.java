package com.handwoong.rainbowletter.letter.domain;

import com.handwoong.rainbowletter.common.service.port.UuidGenerator;
import com.handwoong.rainbowletter.image.domain.Image;
import com.handwoong.rainbowletter.letter.domain.dto.LetterCreate;
import com.handwoong.rainbowletter.pet.domain.Pet;
import jakarta.annotation.Nullable;
import java.time.LocalDateTime;
import lombok.Builder;

@Builder
public record Letter(
        Long id,
        Summary summary,
        Content content,
        String shareLink,
        LetterStatus status,
        Pet pet,
        @Nullable
        Image image,
        @Nullable
        Reply reply,
        @Nullable
        LocalDateTime createdAt
) {
    public static Letter create(final LetterCreate request,
                                final Pet pet,
                                final Image image,
                                final UuidGenerator uuidGenerator
    ) {
        return Letter.builder()
                .summary(request.summary())
                .content(request.content())
                .shareLink(uuidGenerator.generate())
                .status(LetterStatus.REQUEST)
                .pet(pet)
                .image(image)
                .build();
    }

    public Letter update(final Reply reply) {
        return Letter.builder()
                .id(id)
                .summary(summary)
                .content(content)
                .shareLink(shareLink)
                .status(status)
                .pet(pet)
                .image(image)
                .reply(reply)
                .createdAt(createdAt)
                .build();
    }

    public Letter updateStatus() {
        return Letter.builder()
                .id(id)
                .summary(summary)
                .content(content)
                .shareLink(shareLink)
                .status(LetterStatus.RESPONSE)
                .pet(pet)
                .image(image)
                .reply(reply)
                .createdAt(createdAt)
                .build();
    }
}
