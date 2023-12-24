package com.handwoong.rainbowletter.config.security.oauth;

import com.handwoong.rainbowletter.config.security.CustomUserDetails;
import com.handwoong.rainbowletter.domain.member.model.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomOAuthUserService extends DefaultOAuth2UserService {
    private final OAuthLoginManager oAuthLoginManager;

    @Override
    public OAuth2User loadUser(final OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        final OAuth2User oAuth2User = super.loadUser(userRequest);
        final String registrationId = userRequest.getClientRegistration().getRegistrationId();
        final Member member = oAuthLoginManager.process(registrationId, oAuth2User);
        return new CustomUserDetails(member, oAuth2User.getAttributes());
    }
}
