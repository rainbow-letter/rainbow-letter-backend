package com.handwoong.rainbowletter.config.security.handler;

import com.handwoong.rainbowletter.config.security.handler.AuthenticationErrorHandler;
import java.io.IOException;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import com.handwoong.rainbowletter.exception.ErrorCode;

public class CustomAuthenticationEntryPoint extends AuthenticationErrorHandler implements AuthenticationEntryPoint {
    public CustomAuthenticationEntryPoint() {
        super(ErrorCode.UN_AUTHORIZE);
    }

    @Override
    public void commence(final HttpServletRequest request,
                         final HttpServletResponse response,
                         final AuthenticationException authException) throws IOException {
        handle(response);
    }
}
