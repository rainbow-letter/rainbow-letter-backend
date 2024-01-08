package com.handwoong.rainbowletter.letter.infrastructure;

import static org.assertj.core.api.Assertions.assertThat;

import com.handwoong.rainbowletter.letter.domain.ChatGpt;
import com.handwoong.rainbowletter.letter.domain.Content;
import com.handwoong.rainbowletter.letter.domain.Reply;
import com.handwoong.rainbowletter.letter.domain.ReplyReadStatus;
import com.handwoong.rainbowletter.letter.domain.ReplyType;
import com.handwoong.rainbowletter.letter.domain.Summary;
import com.handwoong.rainbowletter.letter.infrastructure.chatgpt.ChatGptEntity;
import java.time.LocalDate;
import org.junit.jupiter.api.Test;

class ReplyEntityTest {
    @Test
    void 답장_도메인으로_엔티티를_생성한다() {
        // given
        final ChatGpt chatGpt = ChatGpt.builder()
                .id(1L)
                .content("이 내용은 ChatGPT가 생성한 내용입니다.")
                .promptTokens(1004)
                .completionTokens(463)
                .totalTokens(1467)
                .build();
        final Reply reply = Reply.builder()
                .id(1L)
                .summary(new Summary("엄마 콩이 여기서 잘 지내!"))
                .content(new Content("엄마 콩이 여기서 잘 지내! 여기 무지개마을은 매일 햇살이 따뜻해. 콩이 언제나 엄마 곁에 있을게. 사랑해!"))
                .readStatus(ReplyReadStatus.UNREAD)
                .type(ReplyType.REPLY)
                .timestamp(LocalDate.now().atStartOfDay())
                .chatGpt(chatGpt)
                .build();

        // when
        final ReplyEntity replyEntity = ReplyEntity.from(reply);

        // then
        assertThat(replyEntity.getId()).isEqualTo(1);
        assertThat(replyEntity.getSummary()).isEqualTo("엄마 콩이 여기서 잘 지내!");
        assertThat(replyEntity.getContent()).isEqualTo(
                "엄마 콩이 여기서 잘 지내! 여기 무지개마을은 매일 햇살이 따뜻해. 콩이 언제나 엄마 곁에 있을게. 사랑해!");
        assertThat(replyEntity.getReadStatus()).isEqualTo(ReplyReadStatus.UNREAD);
        assertThat(replyEntity.getType()).isEqualTo(ReplyType.REPLY);
        assertThat(replyEntity.getTimestamp()).isEqualTo(LocalDate.now().atStartOfDay());
        assertThat(replyEntity.getChatGptEntity()).isEqualTo(ChatGptEntity.from(chatGpt));
    }

    @Test
    void 엔티티로_답장_도메인을_생성한다() {
        final ChatGpt chatGpt = ChatGpt.builder()
                .id(1L)
                .content("이 내용은 ChatGPT가 생성한 내용입니다.")
                .promptTokens(1004)
                .completionTokens(463)
                .totalTokens(1467)
                .build();
        final Reply reply = Reply.builder()
                .id(1L)
                .summary(new Summary("엄마 콩이 여기서 잘 지내!"))
                .content(new Content("엄마 콩이 여기서 잘 지내! 여기 무지개마을은 매일 햇살이 따뜻해. 콩이 언제나 엄마 곁에 있을게. 사랑해!"))
                .readStatus(ReplyReadStatus.UNREAD)
                .type(ReplyType.REPLY)
                .timestamp(LocalDate.now().atStartOfDay())
                .chatGpt(chatGpt)
                .build();

        final ReplyEntity replyEntity = ReplyEntity.from(reply);

        // when
        final Reply convertReply = replyEntity.toModel();

        // then
        assertThat(convertReply.id()).isEqualTo(1);
        assertThat(convertReply.summary()).hasToString("엄마 콩이 여기서 잘 지내!");
        assertThat(convertReply.content()).hasToString(
                "엄마 콩이 여기서 잘 지내! 여기 무지개마을은 매일 햇살이 따뜻해. 콩이 언제나 엄마 곁에 있을게. 사랑해!");
        assertThat(convertReply.readStatus()).isEqualTo(ReplyReadStatus.UNREAD);
        assertThat(convertReply.type()).isEqualTo(ReplyType.REPLY);
        assertThat(convertReply.timestamp()).isEqualTo(LocalDate.now().atStartOfDay());
        assertThat(convertReply.chatGpt()).isEqualTo(chatGpt);
    }
}