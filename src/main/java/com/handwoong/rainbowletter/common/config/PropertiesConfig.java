package com.handwoong.rainbowletter.common.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Getter
@Configuration
public class PropertiesConfig {
    @Value("${chatgpt.token}")
    private String chatgptToken;

    @Value("${airtable.token}")
    private String airtableToken;
}
