package com.handwoong.rainbowletter.common.config.client;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import nl.basjes.parse.useragent.UserAgentAnalyzer;

@Configuration
public class UserAgentConfig {

    @Bean
    public UserAgentAnalyzer userAgentAnalyzer() {
        return UserAgentAnalyzer
                .newBuilder()
                .hideMatcherLoadStats()
                .withCache(10000)
                .build();
    }
}
