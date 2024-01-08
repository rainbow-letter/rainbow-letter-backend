package com.handwoong.rainbowletter.legacy.dto;

import com.handwoong.rainbowletter.letter.domain.dto.chatgpt.ChatGptRequest;

public record ReplyRequestDto(
        String baseId,
        String recordId,
        String tableName,
        ChatGptRequest body
) {
}
