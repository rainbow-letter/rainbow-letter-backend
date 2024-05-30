package com.handwoong.rainbowletter.letter.domain.dto.chatgpt;

import java.util.List;

import lombok.Builder;

@Builder
public record ChatGptRequest(
        String model,
        List<ChatGptPrompt> messages,
        Long max_tokens,
        Double temperature,
        Double top_p,
        Double frequency_penalty,
        Double presence_penalty,
        List<String> stop
) {
    public static ChatGptRequest create(final String model,
                                        final List<ChatGptPrompt> messages,
                                        final Long max_tokens,
                                        final Double temperature,
                                        final Double topP,
                                        final Double frequencyPenalty,
                                        final Double presencePenalty,
                                        final List<String> stop
    ) {
        return ChatGptRequest.builder()
                .model(model)
                .messages(messages)
                .max_tokens(max_tokens)
                .temperature(temperature)
                .top_p(topP)
                .frequency_penalty(frequencyPenalty)
                .presence_penalty(presencePenalty)
                .stop(stop)
                .build();
    }
}
