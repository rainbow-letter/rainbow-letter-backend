package com.handwoong.rainbowletter.dto.member;

import static com.handwoong.rainbowletter.dto.ValidateMessage.EMPTY_MESSAGE;
import static com.handwoong.rainbowletter.dto.ValidateMessage.PASSWORD_FORMAT;
import static com.handwoong.rainbowletter.dto.ValidateMessage.PASSWORD_MESSAGE;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record ChangePasswordRequest(
        @Nullable
        String password,

        @NotBlank(message = EMPTY_MESSAGE)
        @Pattern(regexp = PASSWORD_FORMAT, message = PASSWORD_MESSAGE)
        String newPassword
) {
}
