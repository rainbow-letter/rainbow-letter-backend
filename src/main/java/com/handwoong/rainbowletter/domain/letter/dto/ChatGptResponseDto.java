package com.handwoong.rainbowletter.domain.letter.dto;

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
