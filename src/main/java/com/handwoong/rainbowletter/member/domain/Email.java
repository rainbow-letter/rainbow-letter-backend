package com.handwoong.rainbowletter.member.domain;

import com.handwoong.rainbowletter.member.exception.EmailFormatNotValidException;
import java.util.regex.Pattern;
import org.springframework.util.StringUtils;

public record Email(String email) {
    public static final String EMAIL_REGEX = "^[a-zA-Z0-9+-\\_.*]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+$";

    private static final Pattern EMAIL_PATTERN = Pattern.compile(EMAIL_REGEX);

    public Email {
        validateNull(email);
        validateFormat(email);
    }

    private void validateNull(final String email) {
        if (!StringUtils.hasText(email)) {
            throw new EmailFormatNotValidException(email);
        }
    }

    private void validateFormat(final String email) {
        if (!EMAIL_PATTERN.matcher(email).matches()) {
            throw new EmailFormatNotValidException(email);
        }
    }

    @Override
    public String toString() {
        return email;
    }
}
