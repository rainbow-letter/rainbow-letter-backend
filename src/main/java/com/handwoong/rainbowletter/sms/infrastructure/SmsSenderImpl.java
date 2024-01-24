package com.handwoong.rainbowletter.sms.infrastructure;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.handwoong.rainbowletter.common.config.sms.SmsConfig;
import com.handwoong.rainbowletter.common.util.ProfileManager;
import com.handwoong.rainbowletter.sms.domain.dto.AligoResponse;
import com.handwoong.rainbowletter.sms.domain.dto.SmsCreate;
import com.handwoong.rainbowletter.sms.domain.dto.SmsSend;
import com.handwoong.rainbowletter.sms.service.port.SmsSender;
import java.io.IOException;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Component
@RequiredArgsConstructor
public class SmsSenderImpl implements SmsSender {
    private static final String ALIGO_URL = "https://apis.aligo.in/send/";

    private final SmsConfig smsConfig;
    private final RestTemplate restTemplate;
    private final ProfileManager profileManager;
    private final ObjectMapper mapper;

    @Override
    @Profile("!test")
    public SmsCreate send(final SmsSend request) throws IOException {
        final MultiValueMap<String, String> smsRequestBody = createSmsRequestBody(request);
        final HttpHeaders smsHeaders = new HttpHeaders();
        smsHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        final HttpEntity<MultiValueMap<String, String>> smsEntity = new HttpEntity<>(smsRequestBody, smsHeaders);
        final String body = restTemplate.exchange(ALIGO_URL, HttpMethod.POST, smsEntity, String.class).getBody();
        final AligoResponse response = mapper.readValue(body, AligoResponse.class);
        return SmsCreate.builder()
                .code(response.result_code())
                .statusMessage(response.message())
                .sender(smsConfig.getAligoSender())
                .receiver(request.receiver().phoneNumber())
                .content(request.content())
                .build();
    }

    private MultiValueMap<String, String> createSmsRequestBody(final SmsSend request) {
        final MultiValueMap<String, String> sms = new LinkedMultiValueMap<>();
        final String currentProfile = profileManager.getActiveProfile();
        if (Objects.isNull(currentProfile) || !currentProfile.equals("prod")) {
            sms.add("testmode_yn", "Y");
        }
        sms.add("user_id", "rainbowletter");
        sms.add("key", smsConfig.getAligoAccessKey());
        sms.add("msg", request.content());
        sms.add("receiver", request.receiver().phoneNumber());
        sms.add("sender", smsConfig.getAligoSender());
        sms.add("msg_type", "LMS");
        return sms;
    }
}
