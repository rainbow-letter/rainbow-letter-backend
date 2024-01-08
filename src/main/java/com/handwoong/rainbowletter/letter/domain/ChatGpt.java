package com.handwoong.rainbowletter.letter.domain;

import lombok.Builder;

@Builder
public record ChatGpt(Long id, String content, int promptTokens, int completionTokens, int totalTokens) {
}
