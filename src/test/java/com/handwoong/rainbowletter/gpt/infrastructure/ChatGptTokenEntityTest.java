package com.handwoong.rainbowletter.gpt.infrastructure;

import static org.assertj.core.api.Assertions.assertThat;

import com.handwoong.rainbowletter.gpt.domain.ChatGptToken;
import org.junit.jupiter.api.Test;

class ChatGptTokenEntityTest {
    @Test
    void 토큰_도메인으로_엔티티를_생성한다() {
        // given
        final ChatGptToken chatGptToken = ChatGptToken.builder()
                .id(1L)
                .promptTokens(1004)
                .completionTokens(463)
                .totalTokens(1467)
                .build();

        // when
        final ChatGptTokenEntity chatGptTokenEntity = ChatGptTokenEntity.from(chatGptToken);

        // then
        assertThat(chatGptTokenEntity.getId()).isEqualTo(1);
        assertThat(chatGptTokenEntity.getPromptTokens()).isEqualTo(1004);
        assertThat(chatGptTokenEntity.getCompletionTokens()).isEqualTo(463);
        assertThat(chatGptTokenEntity.getTotalTokens()).isEqualTo(1467);
    }

    @Test
    void 엔티티로_토큰_도메인을_생성한다() {
        // given
        final ChatGptToken chatGptToken = ChatGptToken.builder()
                .id(1L)
                .promptTokens(1004)
                .completionTokens(463)
                .totalTokens(1467)
                .build();

        final ChatGptTokenEntity chatGptTokenEntity = ChatGptTokenEntity.from(chatGptToken);

        // when
        final ChatGptToken convertChatGptToken = chatGptTokenEntity.toModel();

        // then
        assertThat(convertChatGptToken.id()).isEqualTo(1);
        assertThat(convertChatGptToken.promptTokens()).isEqualTo(1004);
        assertThat(convertChatGptToken.completionTokens()).isEqualTo(463);
        assertThat(convertChatGptToken.totalTokens()).isEqualTo(1467);
    }
}
