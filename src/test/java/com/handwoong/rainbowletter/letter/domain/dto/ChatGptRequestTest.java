package com.handwoong.rainbowletter.letter.domain.dto;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.Test;

import com.handwoong.rainbowletter.letter.domain.dto.chatgpt.ChatGptPrompt;
import com.handwoong.rainbowletter.letter.domain.dto.chatgpt.ChatGptRequest;

class ChatGptRequestTest {
    @Test
    void ChatGpt_요청_BODY를_생성한다() {
        // given
        final String model = "gpt-4-1106-preview";
        final List<ChatGptPrompt> messages = List.of(
                ChatGptPrompt.create("system", "You are a bot who replies to a letter in the position of a pet."),
                ChatGptPrompt.create("user", "무지개마을에 살고있는 반려동물에게 쓴 편지에 대한 답장을 작성해줘.")
        );
        final long maxTokens = 1500L;
        final double temperature = 0.8;
        final double topP = 0.7;
        final double frequencyPenalty = 0.2;
        final double presencePenalty = 0.1;
        final List<String> stops = List.of("p.s");

        // when
        final ChatGptRequest request = ChatGptRequest
                .create(model, messages, maxTokens, temperature, topP, frequencyPenalty, presencePenalty, stops);

        // then
        assertThat(request.model()).isEqualTo(model);
        assertThat(request.messages()).isEqualTo(messages);
        assertThat(request.max_tokens()).isEqualTo(maxTokens);
        assertThat(request.temperature()).isEqualTo(temperature);
        assertThat(request.top_p()).isEqualTo(topP);
        assertThat(request.frequency_penalty()).isEqualTo(frequencyPenalty);
        assertThat(request.presence_penalty()).isEqualTo(presencePenalty);
    }
}
