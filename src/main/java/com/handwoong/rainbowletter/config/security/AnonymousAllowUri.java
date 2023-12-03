package com.handwoong.rainbowletter.config.security;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum AnonymousAllowUri implements AllowUri {
    USER_REGISTER("/api/members"),
    USER_LOGIN("/api/members/login"),
    OAUTH_LOGIN("/oauth2/authorization/**"),
    ;

    private final String uri;
}
