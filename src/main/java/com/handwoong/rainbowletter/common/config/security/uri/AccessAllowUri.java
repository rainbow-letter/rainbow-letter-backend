package com.handwoong.rainbowletter.common.config.security.uri;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum AccessAllowUri implements AllowUri {
    ROOT("/"),
    INDEX("/index.html"),
    DATA("/api/data/**"),
    FAQ("/api/faqs/list"),
    PUBLIC_LETTER("/api/letters/share/**"),
    GET_IMAGES("/api/images/resources/**"),
    ;

    private final String uri;
}
