package com.handwoong.rainbowletter.common.config.sms;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Getter
@Configuration
public class SmsConfig {
    @Value("${aligo.access-key}")
    private String aligoAccessKey;

    @Value("${aligo.sender}")
    private String aligoSender;
}
