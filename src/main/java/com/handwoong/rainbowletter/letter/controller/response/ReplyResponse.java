package com.handwoong.rainbowletter.letter.controller.response;

import com.handwoong.rainbowletter.letter.domain.ReplyReadStatus;
import com.handwoong.rainbowletter.letter.domain.ReplyType;
import java.time.LocalDateTime;

public record ReplyResponse(
        Long id,
        String summary,
        String content,
        boolean inspection,
        ReplyReadStatus readStatus,
        ReplyType type,
        LocalDateTime timestamp
) {
}
