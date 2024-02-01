package com.handwoong.rainbowletter.common.config.client;

import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Getter
@Configuration
@NoArgsConstructor
public class ClientConfig {
    @Value("#{${client.url}}")
    private List<String> clientUrl;

    public ClientConfig(final List<String> clientUrl) {
        this.clientUrl = clientUrl;
    }
}
