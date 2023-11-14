package com.handwoong.rainbowletter.config.security;

import java.io.IOException;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import com.handwoong.rainbowletter.exception.ErrorCode;

public class CustomAccessDeniedHandler extends AuthenticationErrorHandler implements AccessDeniedHandler {
    protected CustomAccessDeniedHandler() {
        super(ErrorCode.ACCESS_DENIED);
    }

    @Override
    public void handle(final HttpServletRequest request,
                       final HttpServletResponse response,
                       final AccessDeniedException accessDeniedException) throws IOException {
        handle(response);
    }
}
