package com.handwoong.rainbowletter.member.service;

import com.handwoong.rainbowletter.member.domain.Member;
import com.handwoong.rainbowletter.member.infrastructure.CustomUserDetails;
import com.handwoong.rainbowletter.member.service.port.OAuthLoginService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomOAuthUserService extends DefaultOAuth2UserService {
    private final OAuthLoginService oauthLoginService;

    @Override
    public OAuth2User loadUser(final OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        final OAuth2User oauth2User = super.loadUser(userRequest);
        final String registrationId = userRequest.getClientRegistration().getRegistrationId();
        final Member member = oauthLoginService.process(registrationId, oauth2User);
        return new CustomUserDetails(member, oauth2User.getAttributes());
    }
}
