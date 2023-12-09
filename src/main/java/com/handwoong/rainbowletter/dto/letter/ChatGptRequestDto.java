package com.handwoong.rainbowletter.dto.letter;

import java.util.List;

public record ChatGptRequestDto(
        String model,
        List<ChatGptMessage> messages,
        Long max_tokens,
        Double temperature,
        Double top_p,
        Double frequency_penalty,
        Double presence_penalty
) {
}
