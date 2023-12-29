package com.handwoong.rainbowletter.member.controller.request;

import static com.handwoong.rainbowletter.common.util.ValidateMessage.EMPTY_MESSAGE;
import static com.handwoong.rainbowletter.common.util.ValidateMessage.PASSWORD_MESSAGE;

import com.handwoong.rainbowletter.member.domain.Password;
import com.handwoong.rainbowletter.member.domain.dto.ChangePassword;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record ChangePasswordRequest(
        @NotBlank(message = EMPTY_MESSAGE)
        @Pattern(regexp = Password.PASSWORD_REGEX, message = PASSWORD_MESSAGE)
        String password,

        @NotBlank(message = EMPTY_MESSAGE)
        @Pattern(regexp = Password.PASSWORD_REGEX, message = PASSWORD_MESSAGE)
        String newPassword
) {
    public ChangePassword toDto() {
        return ChangePassword.builder()
                .password(new Password(password))
                .newPassword(new Password(newPassword))
                .build();
    }
}
