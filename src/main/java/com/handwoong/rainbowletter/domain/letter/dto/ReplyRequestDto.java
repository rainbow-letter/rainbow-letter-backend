package com.handwoong.rainbowletter.domain.letter.dto;

public record ReplyRequestDto(
        String baseId,
        String recordId,
        String tableName,
        ChatGptRequestDto body
) {
}
