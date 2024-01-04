package com.handwoong.rainbowletter.letter.controller.response;

import com.handwoong.rainbowletter.image.controller.response.ImageResponse;
import com.handwoong.rainbowletter.letter.domain.Letter;
import java.time.LocalDateTime;
import lombok.Builder;

@Builder
public record LetterResponse(
        Long id,
        String summary,
        String content,
        LetterPetResponse pet,
        ImageResponse image,
        LocalDateTime createdAt
) {
    public static LetterResponse from(final Letter letter) {
        return LetterResponse.builder()
                .id(letter.id())
                .summary(letter.summary().toString())
                .content(letter.content().toString())
                .pet(LetterPetResponse.from(letter.pet()))
                .image(ImageResponse.from(letter.image()))
                .createdAt(letter.createdAt())
                .build();
    }
}
