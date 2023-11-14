package com.handwoong.rainbowletter.config.security;

public record TokenResponse(
        String grantType,
        String token
) {
    public static TokenResponse of(final GrantType grantType, final String token) {
        return new TokenResponse(grantType.getName(), token);
    }
}
