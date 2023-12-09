package com.handwoong.rainbowletter.dto.letter;

import java.util.List;

public record ChatGptResponseDto(
        String id,
        String object,
        int created,
        String model,
        List<ChatGptResponseChoice> choices,
        ChatGptResponseUsage usage
) {
}
