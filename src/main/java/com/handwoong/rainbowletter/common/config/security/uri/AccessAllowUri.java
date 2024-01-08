package com.handwoong.rainbowletter.common.config.security.uri;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum AccessAllowUri implements AllowUri {
    ROOT("/"),
    INDEX("/index.html"),
    FAQ("/api/faqs/list"),
    LEGACY_LETTER("/api/legacy/letters"),
    ;

    private final String uri;
}
