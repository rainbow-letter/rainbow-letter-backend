package com.handwoong.rainbowletter.common.util;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Getter
@Component
public class ProfileManager {
    @Value("${spring.profiles.active}")
    private String activeProfile;
}
