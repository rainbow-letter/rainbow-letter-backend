package com.handwoong.rainbowletter.config.security;

import com.handwoong.rainbowletter.config.security.oauth.CustomOAuthUserService;
import com.handwoong.rainbowletter.config.security.oauth.OAuthSuccessHandler;
import java.util.Arrays;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.cors.CorsConfigurationSource;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final CorsConfigurationSource corsConfigurationSource;
    private final JwtTokenAuthenticationFilter jwtTokenAuthenticationFilter;
    private final CustomOAuthUserService customOAuthUserService;
    private final OAuthSuccessHandler oAuthSuccessHandler;

    @Bean
    public SecurityFilterChain securityFilterChain(final HttpSecurity http) throws Exception {
        addBasicConfigToHttpSecurity(http);
        addAuthorizeConfigToHttpSecurity(http);
        addExceptionHandlerToHttpSecurity(http);
        addFilterBefore(http);
        configurationOAuth2(http);
        return http.build();
    }

    private void addBasicConfigToHttpSecurity(final HttpSecurity http) throws Exception {
        http.formLogin(AbstractHttpConfigurer::disable)
                .httpBasic(AbstractHttpConfigurer::disable)
                .csrf(AbstractHttpConfigurer::disable)
                .cors(cors -> cors.configurationSource(corsConfigurationSource))
                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
    }

    private void addAuthorizeConfigToHttpSecurity(final HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(auth ->
                auth
                        .requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()
                        .requestMatchers(convertUriToPathMatcher(AccessAllowUri.values())).permitAll()
                        .requestMatchers(convertUriToPathMatcher(AnonymousAllowUri.values())).anonymous()
                        .anyRequest().authenticated()
        );
    }

    private void addExceptionHandlerToHttpSecurity(final HttpSecurity http) throws Exception {
        http.exceptionHandling(exceptionHandling ->
                exceptionHandling
                        .authenticationEntryPoint(new CustomAuthenticationEntryPoint())
                        .accessDeniedHandler(new CustomAccessDeniedHandler()));
    }

    private void addFilterBefore(final HttpSecurity http) {
        http.addFilterBefore(jwtTokenAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
    }

    private void configurationOAuth2(final HttpSecurity http) throws Exception {
        http.oauth2Login(oauth ->
                oauth
                        .authorizationEndpoint(endpoint -> endpoint.baseUri("/api/oauth2/authorization/**"))
                        .redirectionEndpoint(endpoint -> endpoint.baseUri("/api/login/oauth2/code/**"))
                        .userInfoEndpoint(userInfo -> userInfo.userService(customOAuthUserService))
                        .successHandler(oAuthSuccessHandler));
    }

    private AntPathRequestMatcher[] convertUriToPathMatcher(final AllowUri[] allowUris) {
        return Arrays.stream(allowUris)
                .map(AllowUri::getUri)
                .map(AntPathRequestMatcher::antMatcher)
                .toArray(AntPathRequestMatcher[]::new);
    }
}
