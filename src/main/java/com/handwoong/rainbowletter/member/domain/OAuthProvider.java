package com.handwoong.rainbowletter.member.domain;

import com.handwoong.rainbowletter.member.exception.OAuthProviderTypeNotValidException;
import java.util.Arrays;

public enum OAuthProvider {
    NONE,
    GOOGLE,
    ;

    public static OAuthProvider match(final String registrationId) {
        return Arrays.stream(OAuthProvider.values())
                .filter(provider -> provider != NONE)
                .filter(provider -> provider.name().toLowerCase().equals(registrationId))
                .findAny()
                .orElseThrow(() -> new OAuthProviderTypeNotValidException(registrationId));
    }
}
