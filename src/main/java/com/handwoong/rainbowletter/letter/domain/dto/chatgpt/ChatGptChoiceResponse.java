package com.handwoong.rainbowletter.letter.domain.dto.chatgpt;

public record ChatGptChoiceResponse(ChatGptPrompt message, int index, String finish_reason) {
}
