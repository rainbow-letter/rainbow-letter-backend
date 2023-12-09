package com.handwoong.rainbowletter.config.security;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum AccessAllowUri implements AllowUri {
    ROOT("/"),
    INDEX("/index.html"),
    EMAIL_VERIFY("/api/members/verify/**"),
    REPLY_LETTER("/api/letters");

    private final String uri;
}
