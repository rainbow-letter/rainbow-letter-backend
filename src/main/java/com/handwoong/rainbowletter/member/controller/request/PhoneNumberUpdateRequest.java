package com.handwoong.rainbowletter.member.controller.request;

import static com.handwoong.rainbowletter.common.util.validation.ValidateMessage.EMPTY_MESSAGE;
import static com.handwoong.rainbowletter.common.util.validation.ValidateMessage.PHONE_NUMBER_MESSAGE;

import com.handwoong.rainbowletter.member.domain.PhoneNumber;
import com.handwoong.rainbowletter.member.domain.dto.PhoneNumberUpdate;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record PhoneNumberUpdateRequest(
        @NotBlank(message = EMPTY_MESSAGE)
        @Pattern(regexp = PhoneNumber.PHONE_NUMBER_REGEX, message = PHONE_NUMBER_MESSAGE)
        String phoneNumber
) {
    public PhoneNumberUpdate toDto() {
        return PhoneNumberUpdate.builder()
                .phoneNumber(new PhoneNumber(phoneNumber))
                .build();
    }
}
