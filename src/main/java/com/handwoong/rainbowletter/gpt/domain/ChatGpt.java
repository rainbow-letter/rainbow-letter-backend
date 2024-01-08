package com.handwoong.rainbowletter.gpt.domain;

import lombok.Builder;

@Builder
public record ChatGpt(Long id, String content, int promptTokens, int completionTokens, int totalTokens) {
}
