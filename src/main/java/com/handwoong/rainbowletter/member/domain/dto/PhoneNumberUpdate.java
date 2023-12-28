package com.handwoong.rainbowletter.member.domain.dto;

import static com.handwoong.rainbowletter.common.util.ValidateMessage.EMPTY_MESSAGE;
import static com.handwoong.rainbowletter.common.util.ValidateMessage.PHONE_NUMBER_MESSAGE;

import com.handwoong.rainbowletter.member.domain.PhoneNumber;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record PhoneNumberUpdate(
        @NotBlank(message = EMPTY_MESSAGE)
        @Pattern(regexp = PhoneNumber.PHONE_NUMBER_REGEX, message = PHONE_NUMBER_MESSAGE)
        String phoneNumber
) {
}
