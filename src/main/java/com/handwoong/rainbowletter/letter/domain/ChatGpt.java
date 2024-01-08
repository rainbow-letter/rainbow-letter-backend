package com.handwoong.rainbowletter.letter.domain;

import lombok.Builder;

@Builder
public record ChatGpt(Long id, String content, int promptTokens, int completionTokens, int totalTokens) {
    public static ChatGpt create(final String content,
                                 final int promptTokens,
                                 final int completionTokens,
                                 final int totalTokens
    ) {
        return ChatGpt.builder()
                .content(content)
                .promptTokens(promptTokens)
                .completionTokens(completionTokens)
                .totalTokens(totalTokens)
                .build();
    }
}
