package com.handwoong.rainbowletter.dto.letter;

public record AirtableUpdateDto(
        String GPT,
        int prompt_tokens,
        int completion_tokens,
        int total_tokens
) {
}
