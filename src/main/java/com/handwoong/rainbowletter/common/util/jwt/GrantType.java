package com.handwoong.rainbowletter.common.util.jwt;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum GrantType {
    PATH_VARIABLE("Path Variable"),
    BEARER("Bearer");

    private final String name;
}
