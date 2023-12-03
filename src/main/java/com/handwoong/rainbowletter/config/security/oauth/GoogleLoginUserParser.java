package com.handwoong.rainbowletter.config.security.oauth;

import org.springframework.security.oauth2.core.user.OAuth2User;

import com.handwoong.rainbowletter.config.security.password.PasswordGenerator;
import com.handwoong.rainbowletter.dto.member.MemberRegisterRequest;

public class GoogleLoginUserParser implements OAuthLoginUserParser {
    @Override
    public MemberRegisterRequest parseDetails(final OAuth2User user, final PasswordGenerator passwordGenerator) {
        final String email = user.getAttributes().get("email").toString();
        final String password = passwordGenerator.generate();
        return new MemberRegisterRequest(email, password);
    }

    @Override
    public String parseProviderId(final OAuth2User user) {
        return user.getAttributes().get("sub").toString();
    }
}
