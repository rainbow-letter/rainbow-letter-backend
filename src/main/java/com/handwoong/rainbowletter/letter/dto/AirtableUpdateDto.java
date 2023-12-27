package com.handwoong.rainbowletter.letter.dto;

public record AirtableUpdateDto(
        String GPT,
        int prompt_tokens,
        int completion_tokens,
        int total_tokens
) {
}
