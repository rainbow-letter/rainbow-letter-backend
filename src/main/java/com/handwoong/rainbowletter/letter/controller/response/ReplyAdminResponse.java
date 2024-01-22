package com.handwoong.rainbowletter.letter.controller.response;

import com.handwoong.rainbowletter.letter.domain.ReplyReadStatus;
import com.handwoong.rainbowletter.letter.domain.ReplyType;
import java.time.LocalDateTime;

public record ReplyAdminResponse(
        Long id,
        String summary,
        String content,
        boolean inspection,
        LocalDateTime inspectionTime,
        ReplyReadStatus readStatus,
        ReplyType type,
        LocalDateTime timestamp,
        String chatGptContent
) {
}
