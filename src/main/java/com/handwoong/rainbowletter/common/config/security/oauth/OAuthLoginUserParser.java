package com.handwoong.rainbowletter.common.config.security.oauth;

import com.handwoong.rainbowletter.common.config.security.password.PasswordGenerator;
import com.handwoong.rainbowletter.member.domain.dto.MemberRegisterRequest;
import org.springframework.security.oauth2.core.user.OAuth2User;

public interface OAuthLoginUserParser {
    MemberRegisterRequest parseDetails(final OAuth2User user, final PasswordGenerator passwordGenerator);

    String parseProviderId(final OAuth2User user);
}
