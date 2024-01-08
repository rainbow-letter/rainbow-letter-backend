package com.handwoong.rainbowletter.letter.service;

import static org.assertj.core.api.Assertions.assertThat;

import com.handwoong.rainbowletter.letter.domain.ChatGpt;
import com.handwoong.rainbowletter.letter.domain.Content;
import com.handwoong.rainbowletter.letter.domain.Reply;
import com.handwoong.rainbowletter.letter.domain.ReplyReadStatus;
import com.handwoong.rainbowletter.letter.domain.ReplyType;
import com.handwoong.rainbowletter.letter.domain.Summary;
import com.handwoong.rainbowletter.letter.domain.dto.ReplySubmit;
import com.handwoong.rainbowletter.mock.letter.LetterTestContainer;
import java.time.LocalDate;
import org.junit.jupiter.api.Test;

class ReplyServiceImplTest {
    @Test
    void 답장을_등록한다() {
        // given
        final ChatGpt chatGpt = ChatGpt.builder()
                .id(1L)
                .content("이 내용은 ChatGPT가 생성한 내용입니다.")
                .promptTokens(1004)
                .completionTokens(463)
                .totalTokens(1467)
                .build();
        final Reply reply = Reply.builder()
                .summary(new Summary("ChatGPT가 생성한 제목입니다."))
                .content(new Content("이 내용은 ChatGPT가 생성한 내용입니다."))
                .readStatus(ReplyReadStatus.UNREAD)
                .type(ReplyType.REPLY)
                .timestamp(LocalDate.now().atStartOfDay())
                .chatGpt(chatGpt)
                .build();
        final ReplySubmit request = ReplySubmit.builder()
                .summary(new Summary("엄마 콩이 여기서 잘 지내!"))
                .content(new Content("엄마 콩이 여기서 잘 지내! 여기 무지개마을은 매일 햇살이 따뜻해. 콩이 언제나 엄마 곁에 있을게. 사랑해!"))
                .build();

        final LetterTestContainer testContainer = new LetterTestContainer();
        testContainer.replyRepository.save(reply);

        // when
        final Reply submittedReply = testContainer.replyService.submit(request, 1L);

        // then
        assertThat(submittedReply.id()).isEqualTo(1L);
        assertThat(submittedReply.summary()).hasToString("엄마 콩이 여기서 잘 지내!");
        assertThat(submittedReply.content()).hasToString(
                "엄마 콩이 여기서 잘 지내! 여기 무지개마을은 매일 햇살이 따뜻해. 콩이 언제나 엄마 곁에 있을게. 사랑해!");
        assertThat(submittedReply.type()).isEqualTo(ReplyType.REPLY);
        assertThat(submittedReply.readStatus()).isEqualTo(ReplyReadStatus.UNREAD);
        assertThat(submittedReply.timestamp()).isEqualTo(LocalDate.now().atStartOfDay());
        assertThat(submittedReply.chatGpt()).isEqualTo(chatGpt);
    }
}
