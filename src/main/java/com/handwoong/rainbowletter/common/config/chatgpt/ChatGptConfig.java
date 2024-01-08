package com.handwoong.rainbowletter.common.config.chatgpt;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Getter
@Configuration
public class ChatGptConfig {
    @Value("${chatgpt.token}")
    private String chatGptToken;
}
