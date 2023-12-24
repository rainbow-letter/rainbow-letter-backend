package com.handwoong.rainbowletter.domain.letter.dto;

public record ChatGptResponseChoice(
        ChatGptMessage message,
        int index,
        String finish_reason
) {
}
