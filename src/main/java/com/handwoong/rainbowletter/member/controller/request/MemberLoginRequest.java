package com.handwoong.rainbowletter.member.controller.request;

import static com.handwoong.rainbowletter.common.util.validation.ValidateMessage.EMPTY_MESSAGE;
import static com.handwoong.rainbowletter.common.util.validation.ValidateMessage.LOGIN_MESSAGE;

import com.handwoong.rainbowletter.member.domain.Email;
import com.handwoong.rainbowletter.member.domain.Password;
import com.handwoong.rainbowletter.member.domain.dto.MemberLogin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record MemberLoginRequest(
        @NotBlank(message = EMPTY_MESSAGE)
        @Pattern(regexp = Email.EMAIL_REGEX, message = LOGIN_MESSAGE)
        String email,

        @NotBlank(message = EMPTY_MESSAGE)
        @Pattern(regexp = Password.PASSWORD_REGEX, message = LOGIN_MESSAGE)
        String password
) {
    public MemberLogin toDto() {
        return MemberLogin.builder()
                .email(new Email(email))
                .password(new Password(password))
                .build();
    }
}
