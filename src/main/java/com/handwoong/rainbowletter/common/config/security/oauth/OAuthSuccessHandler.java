package com.handwoong.rainbowletter.common.config.security.oauth;

import com.handwoong.rainbowletter.common.config.client.ClientConfig;
import com.handwoong.rainbowletter.common.config.security.jwt.GrantType;
import com.handwoong.rainbowletter.common.config.security.jwt.JwtTokenProvider;
import com.handwoong.rainbowletter.common.config.security.jwt.TokenResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

@Component
@RequiredArgsConstructor
public class OAuthSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
    private final ClientConfig clientConfig;
    private final JwtTokenProvider tokenProvider;

    @Override
    public void onAuthenticationSuccess(final HttpServletRequest request,
                                        final HttpServletResponse response,
                                        final Authentication authentication) throws IOException {
        final TokenResponse tokenResponse = tokenProvider.generateToken(GrantType.BEARER, authentication);
        final String targetUrl = UriComponentsBuilder
                .fromUriString(clientConfig.getClientUrl() + "/oauth/success")
                .queryParam("token", tokenResponse.token())
                .toUriString();
        getRedirectStrategy().sendRedirect(request, response, targetUrl);
    }
}
