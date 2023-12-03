package com.handwoong.rainbowletter.config.security.oauth;

import java.io.IOException;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import com.handwoong.rainbowletter.config.PropertiesConfig;
import com.handwoong.rainbowletter.config.security.GrantType;
import com.handwoong.rainbowletter.config.security.JwtTokenProvider;
import com.handwoong.rainbowletter.config.security.TokenResponse;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class OAuthSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
    private final JwtTokenProvider tokenProvider;
    private final PropertiesConfig propertiesConfig;

    @Override
    public void onAuthenticationSuccess(final HttpServletRequest request,
                                        final HttpServletResponse response,
                                        final Authentication authentication) throws IOException {
        final TokenResponse tokenResponse = tokenProvider.generateToken(GrantType.BEARER, authentication);
        final String targetUrl = UriComponentsBuilder
                .fromUriString(propertiesConfig.getClientUrls().get(0) + "/oauth/success")
                .queryParam("token", tokenResponse.token())
                .toUriString();
        getRedirectStrategy().sendRedirect(request, response, targetUrl);
    }
}
