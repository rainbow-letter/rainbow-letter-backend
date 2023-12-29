package com.handwoong.rainbowletter.common.infrastructure;

import com.handwoong.rainbowletter.common.service.port.UuidGenerator;
import java.util.UUID;
import org.springframework.stereotype.Component;

@Component
public class UuidGeneratorImpl implements UuidGenerator {
    @Override
    public String generate() {
        return UUID.randomUUID().toString();
    }
}
