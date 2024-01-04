package com.handwoong.rainbowletter.common.config.security.uri;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum AccessAllowUri implements AllowUri {
    ROOT("/"),
    INDEX("/index.html"),
    REPLY_LETTER("/api/legacy/letters"),
    FAQ("/api/faqs/list"),
    ;

    private final String uri;
}
