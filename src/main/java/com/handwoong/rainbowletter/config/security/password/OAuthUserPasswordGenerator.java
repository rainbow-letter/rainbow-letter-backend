package com.handwoong.rainbowletter.config.security.password;

import java.util.UUID;

public class OAuthUserPasswordGenerator implements PasswordGenerator {
    @Override
    public String generate() {
        return UUID.randomUUID().toString();
    }
}
