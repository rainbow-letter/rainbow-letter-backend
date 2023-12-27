package com.handwoong.rainbowletter.member.domain.dto;

import static com.handwoong.rainbowletter.common.util.ValidateMessage.EMAIL_MESSAGE;
import static com.handwoong.rainbowletter.common.util.ValidateMessage.EMPTY_MESSAGE;
import static com.handwoong.rainbowletter.common.util.ValidateMessage.PASSWORD_FORMAT;
import static com.handwoong.rainbowletter.common.util.ValidateMessage.PASSWORD_MESSAGE;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record MemberRegisterRequest(
        @NotBlank(message = EMPTY_MESSAGE)
        @Email(message = EMAIL_MESSAGE)
        String email,

        @NotBlank(message = EMPTY_MESSAGE)
        @Pattern(regexp = PASSWORD_FORMAT, message = PASSWORD_MESSAGE)
        String password
) {
}
