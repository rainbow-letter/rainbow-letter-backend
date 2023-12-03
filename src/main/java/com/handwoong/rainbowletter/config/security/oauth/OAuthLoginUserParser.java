package com.handwoong.rainbowletter.config.security.oauth;

import org.springframework.security.oauth2.core.user.OAuth2User;

import com.handwoong.rainbowletter.config.security.password.PasswordGenerator;
import com.handwoong.rainbowletter.dto.member.MemberRegisterRequest;

public interface OAuthLoginUserParser {
    MemberRegisterRequest parseDetails(final OAuth2User user, final PasswordGenerator passwordGenerator);

    String parseProviderId(final OAuth2User user);
}
