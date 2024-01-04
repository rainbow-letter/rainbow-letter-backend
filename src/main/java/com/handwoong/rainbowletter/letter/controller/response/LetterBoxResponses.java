package com.handwoong.rainbowletter.letter.controller.response;

import java.util.List;
import lombok.Builder;

@Builder
public record LetterBoxResponses(List<LetterBoxResponse> letters) {
    public static LetterBoxResponses from(final List<LetterBoxResponse> letterBoxResponses) {
        return LetterBoxResponses.builder()
                .letters(letterBoxResponses)
                .build();
    }
}
