package com.handwoong.rainbowletter.letter.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.handwoong.rainbowletter.letter.domain.dto.ReplySubmit;
import java.time.LocalDate;
import java.util.Objects;
import org.junit.jupiter.api.Test;

class ReplyTest {
    @Test
    void 답장을_생성한다() {
        // given
        final ChatGpt chatGpt = ChatGpt.builder()
                .id(1L)
                .content("이 내용은 ChatGPT가 생성한 내용입니다.")
                .promptTokens(1004)
                .completionTokens(463)
                .totalTokens(1467)
                .build();

        // when
        final Reply reply = Reply.create(chatGpt);

        // then
        assertThat(reply.id()).isNull();
        assertThat(reply.summary()).hasToString("이 내용은 ChatGPT가 생성한 내용입니다.".substring(0, 20));
        assertThat(reply.content()).hasToString("이 내용은 ChatGPT가 생성한 내용입니다.");
        assertThat(reply.type()).isEqualTo(ReplyType.CHAT_GPT);
        assertThat(reply.readStatus()).isEqualTo(ReplyReadStatus.UNREAD);
        assertThat(reply.timestamp()).isNull();
        assertThat(reply.chatGpt()).isEqualTo(chatGpt);
    }

    @Test
    void 답장을_등록한다() {
        final ChatGpt chatGpt = ChatGpt.builder()
                .id(1L)
                .content("이 내용은 ChatGPT가 생성한 내용입니다.")
                .promptTokens(1004)
                .completionTokens(463)
                .totalTokens(1467)
                .build();
        final Reply reply = Reply.create(chatGpt).inspect();

        final ReplySubmit request = ReplySubmit.builder()
                .summary(new Summary("답장 최종 제목입니다."))
                .content(new Content("답장 최종 본문입니다."))
                .build();

        // when
        final Reply submittedReply = reply.submit(request);

        // then
        assertThat(submittedReply.id()).isNull();
        assertThat(submittedReply.summary()).hasToString("답장 최종 제목입니다.");
        assertThat(submittedReply.content()).hasToString("답장 최종 본문입니다.");
        assertThat(submittedReply.type()).isEqualTo(ReplyType.REPLY);
        assertThat(submittedReply.readStatus()).isEqualTo(ReplyReadStatus.UNREAD);
        assert Objects.nonNull(submittedReply.timestamp());
        assertThat(submittedReply.timestamp().toLocalDate()).isEqualTo(LocalDate.now());
        assertThat(submittedReply.chatGpt()).isEqualTo(chatGpt);
    }

    @Test
    void 답장의_읽음_상태를_변경한다() {
        // given
        final ChatGpt chatGpt = ChatGpt.builder()
                .id(1L)
                .content("이 내용은 ChatGPT가 생성한 내용입니다.")
                .promptTokens(1004)
                .completionTokens(463)
                .totalTokens(1467)
                .build();
        final Reply reply = Reply.create(chatGpt);

        // when
        final Reply readReply = reply.read();

        // then
        assertThat(readReply.id()).isNull();
        assertThat(readReply.summary()).hasToString("이 내용은 ChatGPT가 생성한 내용입니다.".substring(0, 20));
        assertThat(readReply.content()).hasToString("이 내용은 ChatGPT가 생성한 내용입니다.");
        assertThat(readReply.type()).isEqualTo(ReplyType.CHAT_GPT);
        assertThat(readReply.readStatus()).isEqualTo(ReplyReadStatus.READ);
        assertThat(readReply.timestamp()).isNull();
        assertThat(readReply.chatGpt()).isEqualTo(chatGpt);
    }
}
