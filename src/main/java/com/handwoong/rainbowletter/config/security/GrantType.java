package com.handwoong.rainbowletter.config.security;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum GrantType {
    BEARER("Bearer");

    private final String name;
}
