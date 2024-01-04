package com.handwoong.rainbowletter.gpt.domain;

import lombok.Builder;

@Builder
public record ChatGptToken(Long id, int promptTokens, int completionTokens, int totalTokens) {
}
