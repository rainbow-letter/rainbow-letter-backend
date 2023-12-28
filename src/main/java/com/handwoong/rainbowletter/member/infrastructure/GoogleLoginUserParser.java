package com.handwoong.rainbowletter.member.infrastructure;

import com.handwoong.rainbowletter.member.domain.Email;
import com.handwoong.rainbowletter.member.domain.Password;
import com.handwoong.rainbowletter.member.domain.dto.MemberRegister;
import com.handwoong.rainbowletter.member.infrastructure.password.PasswordGenerator;
import com.handwoong.rainbowletter.member.service.port.OAuthLoginUserParser;
import org.springframework.security.oauth2.core.user.OAuth2User;

public class GoogleLoginUserParser implements OAuthLoginUserParser {
    @Override
    public MemberRegister parseDetails(final OAuth2User user, final PasswordGenerator passwordGenerator) {
        final String email = user.getAttributes().get("email").toString();
        final String password = passwordGenerator.generate();
        return new MemberRegister(new Email(email), new Password(password));
    }

    @Override
    public String parseProviderId(final OAuth2User user) {
        return user.getAttributes().get("sub").toString();
    }
}
