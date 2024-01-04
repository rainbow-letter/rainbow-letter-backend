package com.handwoong.rainbowletter.letter.service;

import com.handwoong.rainbowletter.common.config.PropertiesConfig;
import com.handwoong.rainbowletter.letter.controller.port.LetterLegacyService;
import com.handwoong.rainbowletter.letter.dto.AirtableUpdateDto;
import com.handwoong.rainbowletter.letter.dto.AirtableUpdateRequestDto;
import com.handwoong.rainbowletter.letter.dto.ChatGptRequestDto;
import com.handwoong.rainbowletter.letter.dto.ChatGptResponseDto;
import com.handwoong.rainbowletter.letter.dto.ReplyRequestDto;
import lombok.RequiredArgsConstructor;
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
public class LetterLegacyServiceImpl implements LetterLegacyService {
    private static final String OPEN_API_URL = "https://api.openai.com/v1/chat/completions";

    private final RestTemplate restTemplate;
    private final PropertiesConfig propertiesConfig;

    @Override
    @Async
    public void reply(final ReplyRequestDto replyRequest) {
        final ChatGptResponseDto chatGptResponse = requestChatGpt(replyRequest);
        updateAirtable(replyRequest, chatGptResponse);
    }

    private ChatGptResponseDto requestChatGpt(final ReplyRequestDto replyRequest) {
        final HttpHeaders chatGptHeaders = new HttpHeaders();
        chatGptHeaders.setContentType(MediaType.APPLICATION_JSON);
        chatGptHeaders.add("Authorization", "Bearer " + propertiesConfig.getChatgptToken());
        final HttpEntity<ChatGptRequestDto> chatGptRequest = new HttpEntity<>(replyRequest.body(), chatGptHeaders);
        return restTemplate.exchange(OPEN_API_URL, HttpMethod.POST, chatGptRequest, ChatGptResponseDto.class).getBody();
    }

    private void updateAirtable(final ReplyRequestDto replyRequest, final ChatGptResponseDto chatGptResponse) {
        assert chatGptResponse != null;
        final String output = chatGptResponse.choices().get(0).message().content();
        final int promptTokens = chatGptResponse.usage().prompt_tokens();
        final int completionTokens = chatGptResponse.usage().completion_tokens();
        final int totalTokens = chatGptResponse.usage().total_tokens();

        final HttpHeaders airtableHeaders = new HttpHeaders();
        airtableHeaders.setContentType(MediaType.APPLICATION_JSON);
        airtableHeaders.add("Authorization", "Bearer " + propertiesConfig.getAirtableToken());

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
