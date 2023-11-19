package com.handwoong.rainbowletter.domain.member;

import org.springframework.security.crypto.password.PasswordEncoder;

public class FakePasswordEncoder implements PasswordEncoder {
    @Override
    public String encode(final CharSequence rawPassword) {
        return "encodedPassword";
    }

    @Override
    public boolean matches(final CharSequence rawPassword, final String encodedPassword) {
        return false;
    }
}
