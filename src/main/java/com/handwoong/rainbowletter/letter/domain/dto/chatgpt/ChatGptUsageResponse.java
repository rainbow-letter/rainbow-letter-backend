package com.handwoong.rainbowletter.letter.domain.dto.chatgpt;

public record ChatGptUsageResponse(int prompt_tokens, int completion_tokens, int total_tokens) {
}
