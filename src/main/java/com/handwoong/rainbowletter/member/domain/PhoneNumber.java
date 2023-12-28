package com.handwoong.rainbowletter.member.domain;

import com.handwoong.rainbowletter.member.exception.PhoneNumberFormatNotValidException;
import java.util.regex.Pattern;
import org.springframework.util.StringUtils;

public record PhoneNumber(
        String phoneNumber
) {
    public static final String PHONE_NUMBER_REGEX = "^01[016789]\\d{7,8}$";

    private static final Pattern PHONE_NUMBER_PATTERN = Pattern.compile(PHONE_NUMBER_REGEX);

    public PhoneNumber {
        validateNull(phoneNumber);
        validateFormat(phoneNumber);
    }

    private void validateNull(final String phoneNumber) {
        if (!StringUtils.hasText(phoneNumber)) {
            throw new PhoneNumberFormatNotValidException(phoneNumber);
        }
    }

    private void validateFormat(final String phoneNumber) {
        if (!PHONE_NUMBER_PATTERN.matcher(phoneNumber).matches()) {
            throw new PhoneNumberFormatNotValidException(phoneNumber);
        }
    }

    @Override
    public String toString() {
        return phoneNumber;
    }
}
