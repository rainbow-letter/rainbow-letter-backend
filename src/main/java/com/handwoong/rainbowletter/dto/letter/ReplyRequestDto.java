package com.handwoong.rainbowletter.dto.letter;

public record ReplyRequestDto(
        String baseId,
        String recordId,
        String tableName,
        ChatGptRequestDto body
) {
}
