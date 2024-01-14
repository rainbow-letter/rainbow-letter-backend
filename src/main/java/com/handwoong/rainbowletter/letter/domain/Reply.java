package com.handwoong.rainbowletter.letter.domain;

import com.handwoong.rainbowletter.letter.domain.dto.ReplySubmit;
import com.handwoong.rainbowletter.letter.exception.ReplyInspectionStatusNotValidException;
import jakarta.annotation.Nullable;
import java.time.LocalDateTime;
import lombok.Builder;

@Builder
public record Reply(
        Long id,
        Summary summary,
        Content content,
        boolean inspection,
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
                .inspection(false)
                .type(ReplyType.CHAT_GPT)
                .readStatus(ReplyReadStatus.UNREAD)
                .chatGpt(chatGpt)
                .build();
    }

    public Reply submit(final ReplySubmit submit) {
        if (!inspection) {
            throw new ReplyInspectionStatusNotValidException();
        }
        return Reply.builder()
                .id(id)
                .summary(submit.summary())
                .content(submit.content())
                .inspection(true)
                .type(ReplyType.REPLY)
                .readStatus(readStatus)
                .timestamp(LocalDateTime.now())
                .chatGpt(chatGpt)
                .build();
    }

    public Reply read() {
        return Reply.builder()
                .id(id)
                .summary(summary)
                .content(content)
                .inspection(inspection)
                .type(type)
                .readStatus(ReplyReadStatus.READ)
                .timestamp(timestamp)
                .chatGpt(chatGpt)
                .build();
    }

    public Reply inspect() {
        return Reply.builder()
                .id(id)
                .summary(summary)
                .content(content)
                .inspection(true)
                .type(type)
                .readStatus(readStatus)
                .timestamp(timestamp)
                .chatGpt(chatGpt)
                .build();
    }
}
