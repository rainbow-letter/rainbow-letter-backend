package com.handwoong.rainbowletter.member.domain.dto;

import static com.handwoong.rainbowletter.common.util.ValidateMessage.EMPTY_MESSAGE;
import static com.handwoong.rainbowletter.common.util.ValidateMessage.LOGIN_MESSAGE;
import static com.handwoong.rainbowletter.common.util.ValidateMessage.PASSWORD_FORMAT;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record MemberLoginRequest(
        @NotBlank(message = EMPTY_MESSAGE)
        @Email(message = LOGIN_MESSAGE)
        String email,

        @NotBlank(message = EMPTY_MESSAGE)
        @Pattern(regexp = PASSWORD_FORMAT, message = LOGIN_MESSAGE)
        String password
) {
}
