package com.handwoong.rainbowletter.config.security.uri;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum AdminAllowUri implements AllowUri {
    CREATE_FAQ("/api/faqs"),
    EDIT_FAQ("/api/faqs/**"),
    ;

    private final String uri;
}
