package com.handwoong.rainbowletter.mock;

import org.springframework.security.crypto.password.PasswordEncoder;

public class FakePasswordEncoder implements PasswordEncoder {
    private final String FIXTURE;

    public FakePasswordEncoder(final String FIXTURE) {
        this.FIXTURE = FIXTURE;
    }

    @Override
    public String encode(final CharSequence rawPassword) {
        return rawPassword + FIXTURE;
    }

    @Override
    public boolean matches(final CharSequence rawPassword, final String encodedPassword) {
        return encodedPassword.equals(encode(rawPassword));
    }
}
