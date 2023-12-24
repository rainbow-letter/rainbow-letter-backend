package com.handwoong.rainbowletter.domain.member.dto;

import static com.handwoong.rainbowletter.dto.ValidateMessage.EMPTY_MESSAGE;
import static com.handwoong.rainbowletter.dto.ValidateMessage.PHONE_NUMBER_FORMAT;
import static com.handwoong.rainbowletter.dto.ValidateMessage.PHONE_NUMBER_MESSAGE;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record ChangePhoneNumberRequest(
        @NotBlank(message = EMPTY_MESSAGE)
        @Pattern(regexp = PHONE_NUMBER_FORMAT, message = PHONE_NUMBER_MESSAGE)
        String phoneNumber
) {
}
