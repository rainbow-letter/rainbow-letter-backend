package com.handwoong.rainbowletter.letter.domain.dto.chatgpt;

import lombok.Builder;

@Builder
public record ChatGptPrompt(String role, String content) {
    public static ChatGptPrompt create(final String role, final String content) {
        return ChatGptPrompt.builder()
                .role(role)
                .content(content)
                .build();
    }
}
