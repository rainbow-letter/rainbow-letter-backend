package com.handwoong.rainbowletter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class RainbowLetterApplication {
    public static void main(final String[] args) {
        SpringApplication.run(RainbowLetterApplication.class, args);
    }
}
