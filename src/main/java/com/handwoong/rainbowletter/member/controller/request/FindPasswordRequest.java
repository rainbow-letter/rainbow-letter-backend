package com.handwoong.rainbowletter.member.controller.request;

import static com.handwoong.rainbowletter.common.util.validation.ValidateMessage.EMAIL_MESSAGE;
import static com.handwoong.rainbowletter.common.util.validation.ValidateMessage.EMPTY_MESSAGE;

import com.handwoong.rainbowletter.member.domain.Email;
import com.handwoong.rainbowletter.member.domain.dto.FindPassword;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record FindPasswordRequest(
        @NotBlank(message = EMPTY_MESSAGE)
        @Pattern(regexp = Email.EMAIL_REGEX, message = EMAIL_MESSAGE)
        String email
) {
    public FindPassword toDto() {
        return FindPassword.builder()
                .email(new Email(email))
                .build();
    }
}
