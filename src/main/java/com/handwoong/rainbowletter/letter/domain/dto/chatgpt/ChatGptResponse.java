package com.handwoong.rainbowletter.letter.domain.dto.chatgpt;

import java.util.List;

public record ChatGptResponse(
        String id,
        String object,
        int created,
        String model,
        List<ChatGptChoiceResponse> choices,
        ChatGptUsageResponse usage
) {
}
