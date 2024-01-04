package com.handwoong.rainbowletter.letter.domain;

import com.handwoong.rainbowletter.gpt.domain.ChatGptToken;
import jakarta.annotation.Nullable;
import lombok.Builder;

@Builder
public record Reply(
        Long id,
        Summary summary,
        Content content,
        ReplyType type,
        ReplyReadStatus readStatus,
        @Nullable
        ChatGptToken chatGptToken
) {
}
