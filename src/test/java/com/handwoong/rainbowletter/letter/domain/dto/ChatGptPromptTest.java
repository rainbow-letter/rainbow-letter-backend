package com.handwoong.rainbowletter.letter.domain.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.handwoong.rainbowletter.letter.domain.dto.chatgpt.ChatGptPrompt;
import org.junit.jupiter.api.Test;

class ChatGptPromptTest {
    @Test
    void 프롬프트를_생성한다() {
        // given
        final String role = "system";
        final String content = "You are a bot who replies to a letter in the position of a pet.";

        // when
        final ChatGptPrompt chatGptPrompt = ChatGptPrompt.create(role, content);

        // then
        assertThat(chatGptPrompt.role()).isEqualTo("system");
        assertThat(chatGptPrompt.content())
                .isEqualTo("You are a bot who replies to a letter in the position of a pet.");
    }
}
