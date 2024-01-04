package com.handwoong.rainbowletter.letter.controller.request;

import com.handwoong.rainbowletter.letter.domain.Content;
import com.handwoong.rainbowletter.letter.domain.Summary;
import com.handwoong.rainbowletter.letter.domain.dto.LetterCreate;
import jakarta.annotation.Nullable;

public record LetterCreateRequest(String summary, String content, @Nullable Long image) {
    public LetterCreate toDto() {
        return LetterCreate.builder()
                .summary(new Summary(summary))
                .content(new Content(content))
                .image(image)
                .build();
    }
}
