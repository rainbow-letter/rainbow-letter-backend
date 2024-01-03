package com.handwoong.rainbowletter.member.controller.request;

import static com.handwoong.rainbowletter.common.util.validation.ValidateMessage.EMPTY_MESSAGE;
import static com.handwoong.rainbowletter.common.util.validation.ValidateMessage.PASSWORD_MESSAGE;

import com.handwoong.rainbowletter.member.domain.Password;
import com.handwoong.rainbowletter.member.domain.dto.ResetPassword;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record ResetPasswordRequest(
        @NotBlank(message = EMPTY_MESSAGE)
        @Pattern(regexp = Password.PASSWORD_REGEX, message = PASSWORD_MESSAGE)
        String newPassword
) {
    public ResetPassword toDto() {
        return ResetPassword.builder()
                .newPassword(new Password(newPassword))
                .build();
    }
}
