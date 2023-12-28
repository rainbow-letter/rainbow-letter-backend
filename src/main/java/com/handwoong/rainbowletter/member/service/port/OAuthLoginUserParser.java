package com.handwoong.rainbowletter.member.service.port;

import com.handwoong.rainbowletter.member.domain.dto.MemberRegister;
import com.handwoong.rainbowletter.member.infrastructure.password.PasswordGenerator;
import org.springframework.security.oauth2.core.user.OAuth2User;

public interface OAuthLoginUserParser {
    MemberRegister parseDetails(OAuth2User user, PasswordGenerator passwordGenerator);

    String parseProviderId(OAuth2User user);
}
