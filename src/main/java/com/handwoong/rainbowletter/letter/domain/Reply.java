package com.handwoong.rainbowletter.letter.domain;

import jakarta.annotation.Nullable;
import java.time.LocalDateTime;
import lombok.Builder;

@Builder
public record Reply(
        Long id,
        Summary summary,
        Content content,
        ReplyType type,
        ReplyReadStatus readStatus,
        @Nullable
        LocalDateTime timestamp,
        ChatGpt chatGpt
) {
    public static Reply create(final ChatGpt chatGpt) {
        return Reply.builder()
                .summary(new Summary(chatGpt.content().substring(0, 20)))
                .content(new Content(chatGpt.content()))
                .type(ReplyType.CHAT_GPT)
                .readStatus(ReplyReadStatus.UNREAD)
                .chatGpt(chatGpt)
                .build();
    }
}
