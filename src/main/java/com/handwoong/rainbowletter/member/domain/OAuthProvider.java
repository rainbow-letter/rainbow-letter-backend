package com.handwoong.rainbowletter.member.domain;

import java.util.Arrays;

import com.handwoong.rainbowletter.member.exception.OAuthProviderTypeNotValidException;

public enum OAuthProvider {
    NONE,
    GOOGLE,
    NAVER,
    ;

    public static OAuthProvider match(final String registrationId) {
        return Arrays.stream(OAuthProvider.values())
                .filter(provider -> provider != NONE)
                .filter(provider -> provider.name().toLowerCase().equals(registrationId))
                .findAny()
                .orElseThrow(() -> new OAuthProviderTypeNotValidException(registrationId));
    }
}
