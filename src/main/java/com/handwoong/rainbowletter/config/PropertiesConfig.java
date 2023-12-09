package com.handwoong.rainbowletter.config;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import lombok.Getter;

@Getter
@Configuration
public class PropertiesConfig {
    @Value("${jwt.secret}")
    private String secretKey;

    @Value("#{${client.url}}")
    private List<String> clientUrls;

    @Value("${chatgpt.token}")
    private String chatgptToken;

    @Value("${airtable.token}")
    private String airtableToken;
}
