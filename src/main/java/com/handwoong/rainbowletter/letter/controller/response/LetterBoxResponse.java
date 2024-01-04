package com.handwoong.rainbowletter.letter.controller.response;

import com.handwoong.rainbowletter.letter.domain.Letter;
import com.handwoong.rainbowletter.letter.domain.LetterStatus;
import java.time.LocalDateTime;
import lombok.Builder;

@Builder
public record LetterBoxResponse(
        Long id,
        String summary,
        LetterStatus status,
        String petName,
        LocalDateTime createdAt
) {
    public static LetterBoxResponse from(final Letter letter) {
        return LetterBoxResponse.builder()
                .id(letter.id())
                .summary(letter.summary().toString())
                .status(letter.status())
                .petName(letter.pet().name())
                .createdAt(letter.createdAt())
                .build();
    }
}
