package com.handwoong.rainbowletter.mock.common;

import com.handwoong.rainbowletter.common.service.port.UuidGenerator;

public class FakeUuidGenerator implements UuidGenerator {
    @Override
    public String generate() {
        return "aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa";
    }
}
