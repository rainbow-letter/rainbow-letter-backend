package com.handwoong.rainbowletter.member.infrastructure.oauth;

import java.util.EnumMap;
import java.util.Map;

import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Component;

import com.handwoong.rainbowletter.common.service.port.UuidGenerator;
import com.handwoong.rainbowletter.member.domain.OAuthProvider;
import com.handwoong.rainbowletter.member.domain.dto.MemberRegister;

@Component
public class OAuthLoginUserParsingManager {
    private final Map<OAuthProvider, OAuthLoginUserParser> parsers = new EnumMap<>(OAuthProvider.class);

    public OAuthLoginUserParsingManager() {
        parsers.put(OAuthProvider.GOOGLE, new GoogleLoginUserParser());
        parsers.put(OAuthProvider.NAVER, new NaverLoginUserParser());
        parsers.put(OAuthProvider.KAKAO, new KakaoLoginUserParser());
    }

    public MemberRegister getRegisterRequest(final OAuthProvider provider,
                                             final OAuth2User user,
                                             final UuidGenerator uuidGenerator) {
        final OAuthLoginUserParser parser = parsers.get(provider);
        return parser.parseDetails(user, uuidGenerator);
    }

    public String getProviderId(final OAuthProvider provider, final OAuth2User user) {
        final OAuthLoginUserParser parser = parsers.get(provider);
        return parser.parseProviderId(user);
    }
}
