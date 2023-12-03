package com.handwoong.rainbowletter.config.security.oauth;

import java.util.Arrays;

import com.handwoong.rainbowletter.exception.ErrorCode;
import com.handwoong.rainbowletter.exception.RainbowLetterException;

public enum OAuthProvider {
    NONE,
    GOOGLE,
    ;

    public static OAuthProvider match(final String registrationId) {
        return Arrays.stream(OAuthProvider.values())
                .filter(provider -> provider != NONE)
                .filter(provider -> provider.name().toLowerCase().equals(registrationId))
                .findAny()
                .orElseThrow(() -> new RainbowLetterException(ErrorCode.INVALID_OAUTH_PROVIDER_TYPE, registrationId));
    }
}
