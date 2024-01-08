package com.handwoong.rainbowletter.letter.domain;

import com.handwoong.rainbowletter.letter.domain.dto.ReplySubmit;
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

    public Reply submit(final ReplySubmit submit) {
        return Reply.builder()
                .summary(submit.summary())
                .content(submit.content())
                .type(ReplyType.REPLY)
                .readStatus(readStatus)
                .timestamp(LocalDateTime.now())
                .chatGpt(chatGpt)
                .build();
    }
}
