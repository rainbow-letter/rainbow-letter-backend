package com.handwoong.rainbowletter.member.infrastructure.password;

import java.util.UUID;

public class OAuthUserPasswordGenerator implements PasswordGenerator {
    @Override
    public String generate() {
        return UUID.randomUUID().toString();
    }
}
