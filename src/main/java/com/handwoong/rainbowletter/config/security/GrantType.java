package com.handwoong.rainbowletter.config.security;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum GrantType {
    PATH_VARIABLE("Path Variable"),
    BEARER("Bearer");

    private final String name;
}
