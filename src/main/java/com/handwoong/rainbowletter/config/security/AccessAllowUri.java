package com.handwoong.rainbowletter.config.security;

import java.util.Arrays;

import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum AccessAllowUri {
    ROOT("/"),
    INDEX("/index.html");

    private final String uri;

    public static AntPathRequestMatcher[] convertPathMatcher() {
        return Arrays.stream(AccessAllowUri.values())
                .map(AccessAllowUri::getUri)
                .map(AntPathRequestMatcher::antMatcher)
                .toArray(AntPathRequestMatcher[]::new);
    }
}