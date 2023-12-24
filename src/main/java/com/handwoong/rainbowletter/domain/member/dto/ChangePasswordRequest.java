package com.handwoong.rainbowletter.domain.member.dto;

import static com.handwoong.rainbowletter.util.ValidateMessage.EMPTY_MESSAGE;
import static com.handwoong.rainbowletter.util.ValidateMessage.PASSWORD_FORMAT;
import static com.handwoong.rainbowletter.util.ValidateMessage.PASSWORD_MESSAGE;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record ChangePasswordRequest(
        @Nullable
        @Pattern(regexp = PASSWORD_FORMAT, message = PASSWORD_MESSAGE)
        String password,

        @NotBlank(message = EMPTY_MESSAGE)
        @Pattern(regexp = PASSWORD_FORMAT, message = PASSWORD_MESSAGE)
        String newPassword
) {
}
