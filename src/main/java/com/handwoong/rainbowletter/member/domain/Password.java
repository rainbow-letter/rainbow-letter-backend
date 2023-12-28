package com.handwoong.rainbowletter.member.domain;

import com.handwoong.rainbowletter.member.exception.PasswordFormatNotValidException;
import com.handwoong.rainbowletter.member.exception.PasswordNotMatchedException;
import java.util.regex.Pattern;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.StringUtils;

public record Password(
        String password
) {
    public static final String PASSWORD_REGEX = "^(?=.*[a-zA-Z])(?=.*[0-9])[a-zA-Z0-9!@#$%^&*()-_=+`~]{8,}$";

    private static final Pattern PASSWORD_PATTERN = Pattern.compile(PASSWORD_REGEX);

    public Password {
        validateNull(password);
        validateFormat(password);
    }

    private void validateNull(final String password) {
        if (!StringUtils.hasText(password)) {
            throw new PasswordFormatNotValidException();
        }
    }

    private void validateFormat(final String password) {
        if (!PASSWORD_PATTERN.matcher(password).matches()) {
            throw new PasswordFormatNotValidException();
        }
    }

    public Password encode(final PasswordEncoder passwordEncoder) {
        final String encodedPassword = passwordEncoder.encode(password);
        return new Password(encodedPassword);
    }

    public void compare(final PasswordEncoder passwordEncoder, final Password password) {
        if (!passwordEncoder.matches(password.toString(), this.password)) {
            throw new PasswordNotMatchedException();
        }
    }

    @Override
    public String toString() {
        return password;
    }
}
