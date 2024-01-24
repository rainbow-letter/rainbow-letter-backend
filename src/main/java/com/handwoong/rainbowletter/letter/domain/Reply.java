package com.handwoong.rainbowletter.letter.domain;

import com.handwoong.rainbowletter.letter.domain.dto.ReplyUpdate;
import com.handwoong.rainbowletter.letter.exception.AlreadyReplyException;
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
        @Nullable LocalDateTime inspectionTime,
        ReplyType type,
        ReplyReadStatus readStatus,
        @Nullable LocalDateTime timestamp,
        ChatGpt chatGpt
) {
    public static Reply create(final ChatGpt chatGpt) {
        return Reply.builder()
                .summary(new Summary(
                        chatGpt.content().length() > 20 ? chatGpt.content().substring(0, 20) : chatGpt.content()))
                .content(new Content(chatGpt.content()))
                .inspection(false)
                .type(ReplyType.CHAT_GPT)
                .readStatus(ReplyReadStatus.UNREAD)
                .chatGpt(chatGpt)
                .build();
    }

    public Reply submit() {
        validateSubmit();
        return Reply.builder()
                .id(id)
                .summary(summary)
                .content(content)
                .inspection(true)
                .inspectionTime(inspectionTime)
                .type(ReplyType.REPLY)
                .readStatus(readStatus)
                .timestamp(LocalDateTime.now())
                .chatGpt(chatGpt)
                .build();
    }

    private void validateSubmit() {
        if (!inspection) {
            throw new ReplyInspectionStatusNotValidException();
        }
        validateTypeIsNotReply();
    }

    private void validateTypeIsNotReply() {
        if (type == ReplyType.REPLY) {
            throw new AlreadyReplyException(id);
        }
    }

    public Reply read() {
        return Reply.builder()
                .id(id)
                .summary(summary)
                .content(content)
                .inspection(inspection)
                .inspectionTime(inspectionTime)
                .type(type)
                .readStatus(ReplyReadStatus.READ)
                .timestamp(timestamp)
                .chatGpt(chatGpt)
                .build();
    }

    public Reply inspect() {
        validateTypeIsNotReply();
        final boolean updateInspection = !inspection;
        return Reply.builder()
                .id(id)
                .summary(summary)
                .content(content)
                .inspection(updateInspection)
                .inspectionTime(updateInspection ? LocalDateTime.now() : null)
                .type(type)
                .readStatus(readStatus)
                .timestamp(timestamp)
                .chatGpt(chatGpt)
                .build();
    }

    public Reply update(final ReplyUpdate request) {
        return Reply.builder()
                .id(id)
                .summary(request.summary())
                .content(request.content())
                .inspection(inspection)
                .inspectionTime(inspectionTime)
                .type(type)
                .readStatus(readStatus)
                .timestamp(timestamp)
                .chatGpt(chatGpt)
                .build();
    }
}
