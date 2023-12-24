package com.handwoong.rainbowletter.config.security.handler;

import com.handwoong.rainbowletter.exception.ErrorCode;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

public class CustomAccessDeniedHandler extends AuthenticationErrorHandler implements AccessDeniedHandler {
    public CustomAccessDeniedHandler() {
        super(ErrorCode.ACCESS_DENIED);
    }

    @Override
    public void handle(final HttpServletRequest request,
                       final HttpServletResponse response,
                       final AccessDeniedException accessDeniedException) throws IOException {
        handle(response);
    }
}
