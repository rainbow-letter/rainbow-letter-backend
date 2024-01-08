package com.handwoong.rainbowletter.common.config.security.uri;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum AdminAllowUri implements AllowUri {
    CREATE_FAQ("/api/faqs"),
    EDIT_FAQ("/api/faqs/**"),
    REPLY("/api/replies/admin/**"),
    ;

    private final String uri;
}
