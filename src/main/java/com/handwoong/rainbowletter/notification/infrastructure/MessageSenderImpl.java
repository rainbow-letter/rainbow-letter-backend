package com.handwoong.rainbowletter.notification.infrastructure;

import java.io.IOException;
import java.util.Objects;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.handwoong.rainbowletter.common.config.notification.NotificationConfig;
import com.handwoong.rainbowletter.common.util.ProfileManager;
import com.handwoong.rainbowletter.notification.application.port.MessageSender;
import com.handwoong.rainbowletter.notification.dto.AlimTalkRequest;
import com.handwoong.rainbowletter.sms.domain.dto.AligoResponse;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class MessageSenderImpl implements MessageSender {
    private static final String ALIGO_URL = "https://kakaoapi.aligo.in/akv10/alimtalk/send/";

    private final NotificationConfig notificationConfig;
    private final RestTemplate restTemplate;
    private final ProfileManager profileManager;
    private final ObjectMapper mapper;

    @Override
    public AligoResponse send(final AlimTalkRequest request) throws IOException {
        final MultiValueMap<String, String> requestBody = createRequestBody(request);
        final HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        final HttpEntity<MultiValueMap<String, String>> httpEntity = new HttpEntity<>(requestBody, headers);
        final String body = restTemplate.exchange(ALIGO_URL, HttpMethod.POST, httpEntity, String.class).getBody();
        return mapper.readValue(body, AligoResponse.class);
    }

    private MultiValueMap<String, String> createRequestBody(final AlimTalkRequest request) throws IOException {
        final MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        final String currentProfile = profileManager.getActiveProfile();
        if (Objects.isNull(currentProfile) || !currentProfile.equals("prod")) {
            body.add("testMode", "Y");
        }
        body.add("apikey", notificationConfig.getAligoAccessKey());
        body.add("userid", "rainbowletter");
        body.add("senderkey", notificationConfig.getAligoSenderKey());
        body.add("tpl_code", request.templateCode());
        body.add("sender", notificationConfig.getAligoSender());
        body.add("receiver_1", request.receiver().phoneNumber());
        body.add("subject_1", request.subject());
        body.add("message_1", request.content());
        if (request.useEmTitle()) {
            body.add("emtitle_1", request.subject());
        }
        body.add("button_1", mapper.writeValueAsString(request.buttons()));
        body.add("failover", "Y");
        body.add("fsubject_1", request.failSubject());
        body.add("fmessage_1", request.failContent());
        return body;
    }
}
