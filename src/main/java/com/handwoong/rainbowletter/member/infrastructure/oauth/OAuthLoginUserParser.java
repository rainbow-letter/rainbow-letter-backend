package com.handwoong.rainbowletter.member.infrastructure.oauth;

import com.handwoong.rainbowletter.common.service.port.UuidGenerator;
import com.handwoong.rainbowletter.member.domain.dto.MemberRegister;
import org.springframework.security.oauth2.core.user.OAuth2User;

public interface OAuthLoginUserParser {
    MemberRegister parseDetails(OAuth2User user, UuidGenerator passwordGenerator);

    String parseProviderId(OAuth2User user);
}
