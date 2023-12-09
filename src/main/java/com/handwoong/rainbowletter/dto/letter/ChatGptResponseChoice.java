package com.handwoong.rainbowletter.dto.letter;

public record ChatGptResponseChoice(
        ChatGptMessage message,
        int index,
        String finish_reason
) {
}
