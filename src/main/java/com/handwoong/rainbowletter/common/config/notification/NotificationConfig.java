package com.handwoong.rainbowletter.common.config.notification;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import lombok.Getter;

@Getter
@Configuration
public class NotificationConfig {
    @Value("${aligo.access-key}")
    private String aligoAccessKey;

    @Value("${aligo.sender-key}")
    private String aligoSenderKey;

    @Value("${aligo.sender}")
    private String aligoSender;

    @Value("${naver.commerce.id}")
    private String commerceId;

    @Value("${naver.commerce.secret}")
    private String commerceSecret;
}
