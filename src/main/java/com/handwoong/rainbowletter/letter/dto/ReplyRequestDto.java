package com.handwoong.rainbowletter.letter.dto;

public record ReplyRequestDto(
        String baseId,
        String recordId,
        String tableName,
        ChatGptRequestDto body
) {
}
