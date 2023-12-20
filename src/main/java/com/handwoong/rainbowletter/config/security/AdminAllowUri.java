package com.handwoong.rainbowletter.config.security;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum AdminAllowUri implements AllowUri {
    CREATE_FAQ("/api/faqs"),
    ;

    private final String uri;
}
