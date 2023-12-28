package com.handwoong.rainbowletter.member.service.port;

import com.handwoong.rainbowletter.common.exception.ErrorCode;
import com.handwoong.rainbowletter.common.exception.RainbowLetterException;
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
                .orElseThrow(() -> new RainbowLetterException(ErrorCode.INVALID_OAUTH_PROVIDER_TYPE, registrationId));
    }
}
