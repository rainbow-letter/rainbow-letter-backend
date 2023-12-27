package com.handwoong.rainbowletter.common.util;

import com.handwoong.rainbowletter.common.exception.ErrorCode;
import com.handwoong.rainbowletter.common.exception.RainbowLetterException;
import java.util.Objects;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SecurityUtils {
    public static String getAuthenticationUsername() {
        final Authentication authentication = getAuthentication();
        if (Objects.isNull(authentication) || authentication.getPrincipal() instanceof String) {
            throw new RainbowLetterException(ErrorCode.UN_AUTHORIZE);
        }
        return authentication.getName();
    }

    private static Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }
}
