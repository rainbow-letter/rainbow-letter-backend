package com.handwoong.rainbowletter.member.infrastructure.oauth;

import java.util.Map;

import org.springframework.security.oauth2.core.user.OAuth2User;

import com.handwoong.rainbowletter.common.service.port.UuidGenerator;
import com.handwoong.rainbowletter.member.domain.Email;
import com.handwoong.rainbowletter.member.domain.Password;
import com.handwoong.rainbowletter.member.domain.dto.MemberRegister;

public class NaverLoginUserParser implements OAuthLoginUserParser {
    @Override
    public MemberRegister parseDetails(final OAuth2User user, final UuidGenerator uuidGenerator) {
        final String email = ((Map<?, ?>) user.getAttributes().get("response")).get("email").toString();
        final String password = uuidGenerator.generate();
        return MemberRegister.builder()
                .email(new Email(email))
                .password(new Password(password))
                .build();
    }

    @Override
    public String parseProviderId(final OAuth2User user) {
        return ((Map<?, ?>) user.getAttributes().get("response")).get("id").toString();
    }
}
