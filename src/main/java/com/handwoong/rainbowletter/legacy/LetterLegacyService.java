package com.handwoong.rainbowletter.legacy;

import com.handwoong.rainbowletter.common.config.chatgpt.ChatGptConfig;
import com.handwoong.rainbowletter.legacy.dto.AirtableUpdateDto;
import com.handwoong.rainbowletter.legacy.dto.AirtableUpdateRequestDto;
import com.handwoong.rainbowletter.legacy.dto.ReplyRequestDto;
import com.handwoong.rainbowletter.letter.domain.dto.chatgpt.ChatGptRequest;
import com.handwoong.rainbowletter.letter.domain.dto.chatgpt.ChatGptResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class LetterLegacyService {
    private static final String OPEN_API_URL = "https://api.openai.com/v1/chat/completions";

    private final RestTemplate restTemplate;
    private final ChatGptConfig chatGptConfig;

    @Value("${airtable.token}")
    private String airTableToken;

    @Async
    public void reply(final ReplyRequestDto replyRequest) {
        final ChatGptResponse chatGptResponse = requestChatGpt(replyRequest);
        updateAirtable(replyRequest, chatGptResponse);
    }

    private ChatGptResponse requestChatGpt(final ReplyRequestDto replyRequest) {
        final HttpHeaders chatGptHeaders = new HttpHeaders();
        chatGptHeaders.setContentType(MediaType.APPLICATION_JSON);
        chatGptHeaders.add("Authorization", "Bearer " + chatGptConfig.getChatGptToken());
        final HttpEntity<ChatGptRequest> chatGptRequest = new HttpEntity<>(replyRequest.body(), chatGptHeaders);
        return restTemplate.exchange(OPEN_API_URL, HttpMethod.POST, chatGptRequest, ChatGptResponse.class).getBody();
    }

    private void updateAirtable(final ReplyRequestDto replyRequest, final ChatGptResponse chatGptResponse) {
        assert chatGptResponse != null;
        final String output = chatGptResponse.choices().get(0).message().content();
        final int promptTokens = chatGptResponse.usage().prompt_tokens();
        final int completionTokens = chatGptResponse.usage().completion_tokens();
        final int totalTokens = chatGptResponse.usage().total_tokens();

        final HttpHeaders airtableHeaders = new HttpHeaders();
        airtableHeaders.setContentType(MediaType.APPLICATION_JSON);
        airtableHeaders.add("Authorization", "Bearer " + airTableToken);

        final String airtableUrl = "https://api.airtable.com/v0/"
                + replyRequest.baseId()
                + "/"
                + replyRequest.tableName()
                + "/"
                + replyRequest.recordId();

        final AirtableUpdateDto airtableUpdateDto =
                new AirtableUpdateDto(output, promptTokens, completionTokens, totalTokens);
        final HttpEntity<AirtableUpdateRequestDto> airtableRequest =
                new HttpEntity<>(new AirtableUpdateRequestDto(airtableUpdateDto), airtableHeaders);
        restTemplate.exchange(airtableUrl, HttpMethod.PATCH, airtableRequest, Object.class);
    }
}
