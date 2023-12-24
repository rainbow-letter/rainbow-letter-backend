package com.handwoong.rainbowletter.domain.letter.dto;

public record ChatGptMessage(
        String role,
        String content
) {
}
