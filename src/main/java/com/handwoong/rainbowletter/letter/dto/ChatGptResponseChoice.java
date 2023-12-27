package com.handwoong.rainbowletter.letter.dto;

public record ChatGptResponseChoice(
        ChatGptMessage message,
        int index,
        String finish_reason
) {
}
