package com.handwoong.rainbowletter.config.security;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum AccessAllowUri implements AllowUri {
    ROOT("/"),
    INDEX("/index.html"),
    REPLY_LETTER("/api/letters"),
    FAQ("/api/faqs/list"),
    ;

    private final String uri;
}
