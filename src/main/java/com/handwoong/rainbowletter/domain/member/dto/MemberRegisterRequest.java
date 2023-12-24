package com.handwoong.rainbowletter.domain.member.dto;

import static com.handwoong.rainbowletter.dto.ValidateMessage.EMAIL_MESSAGE;
import static com.handwoong.rainbowletter.dto.ValidateMessage.EMPTY_MESSAGE;
import static com.handwoong.rainbowletter.dto.ValidateMessage.PASSWORD_FORMAT;
import static com.handwoong.rainbowletter.dto.ValidateMessage.PASSWORD_MESSAGE;

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
