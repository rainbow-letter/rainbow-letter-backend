package com.handwoong.rainbowletter.member.service.port;

import com.handwoong.rainbowletter.member.domain.Member;
import org.springframework.security.oauth2.core.user.OAuth2User;

public interface OAuthLoginService {
    Member process(String registrationId, OAuth2User user);
}
