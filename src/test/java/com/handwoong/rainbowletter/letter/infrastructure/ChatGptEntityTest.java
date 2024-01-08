package com.handwoong.rainbowletter.letter.infrastructure;

import static org.assertj.core.api.Assertions.assertThat;

import com.handwoong.rainbowletter.letter.domain.ChatGpt;
import org.junit.jupiter.api.Test;

class ChatGptEntityTest {
    @Test
    void GPT_도메인으로_엔티티를_생성한다() {
        // given
        final ChatGpt chatGpt = ChatGpt.builder()
                .id(1L)
                .content("이 내용은 ChatGPT가 생성한 내용입니다.")
                .promptTokens(1004)
                .completionTokens(463)
                .totalTokens(1467)
                .build();

        // when
        final ChatGptEntity chatGptTokenEntity = ChatGptEntity.from(chatGpt);

        // then
        assertThat(chatGptTokenEntity.getId()).isEqualTo(1);
        assertThat(chatGptTokenEntity.getContent()).isEqualTo("이 내용은 ChatGPT가 생성한 내용입니다.");
        assertThat(chatGptTokenEntity.getPromptTokens()).isEqualTo(1004);
        assertThat(chatGptTokenEntity.getCompletionTokens()).isEqualTo(463);
        assertThat(chatGptTokenEntity.getTotalTokens()).isEqualTo(1467);
    }

    @Test
    void 엔티티로_GPT_도메인을_생성한다() {
        // given
        final ChatGpt chatGpt = ChatGpt.builder()
                .id(1L)
                .content("이 내용은 ChatGPT가 생성한 내용입니다.")
                .promptTokens(1004)
                .completionTokens(463)
                .totalTokens(1467)
                .build();

        final ChatGptEntity chatGptTokenEntity = ChatGptEntity.from(chatGpt);

        // when
        final ChatGpt convertChatGpt = chatGptTokenEntity.toModel();

        // then
        assertThat(convertChatGpt.id()).isEqualTo(1);
        assertThat(convertChatGpt.content()).isEqualTo("이 내용은 ChatGPT가 생성한 내용입니다.");
        assertThat(convertChatGpt.promptTokens()).isEqualTo(1004);
        assertThat(convertChatGpt.completionTokens()).isEqualTo(463);
        assertThat(convertChatGpt.totalTokens()).isEqualTo(1467);
    }
}
