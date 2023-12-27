package com.handwoong.rainbowletter.member.domain.dto;

import static com.handwoong.rainbowletter.common.util.ValidateMessage.EMPTY_MESSAGE;
import static com.handwoong.rainbowletter.common.util.ValidateMessage.PHONE_NUMBER_FORMAT;
import static com.handwoong.rainbowletter.common.util.ValidateMessage.PHONE_NUMBER_MESSAGE;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record ChangePhoneNumberRequest(
        @NotBlank(message = EMPTY_MESSAGE)
        @Pattern(regexp = PHONE_NUMBER_FORMAT, message = PHONE_NUMBER_MESSAGE)
        String phoneNumber
) {
}
