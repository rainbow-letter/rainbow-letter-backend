package com.handwoong.rainbowletter.letter.infrastructure.chatgpt;

import com.handwoong.rainbowletter.common.config.chatgpt.ChatGptConfig;
import com.handwoong.rainbowletter.letter.domain.dto.chatgpt.ChatGptRequest;
import com.handwoong.rainbowletter.letter.domain.dto.chatgpt.ChatGptResponse;
import com.handwoong.rainbowletter.letter.service.port.ChatGptExecutor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
@RequiredArgsConstructor
public class ChatGptExecutorImpl implements ChatGptExecutor {
    private static final String OPEN_AI_URL = "https://api.openai.com/v1/chat/completions";

    private final RestTemplate restTemplate;
    private final ChatGptConfig chatGptConfig;

    @Override
    public ChatGptResponse execute(final ChatGptRequest request) {
        final HttpHeaders chatGptHeaders = new HttpHeaders();
        chatGptHeaders.setContentType(MediaType.APPLICATION_JSON);
        chatGptHeaders.add("Authorization", "Bearer " + chatGptConfig.getChatGptToken());
        final HttpEntity<ChatGptRequest> chatGptRequest = new HttpEntity<>(request, chatGptHeaders);
        return restTemplate.exchange(OPEN_AI_URL, HttpMethod.POST, chatGptRequest, ChatGptResponse.class).getBody();
    }
}
