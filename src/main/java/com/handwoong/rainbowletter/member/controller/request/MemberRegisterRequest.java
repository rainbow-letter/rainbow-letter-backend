package com.handwoong.rainbowletter.member.controller.request;

import static com.handwoong.rainbowletter.common.util.validation.ValidateMessage.EMAIL_MESSAGE;
import static com.handwoong.rainbowletter.common.util.validation.ValidateMessage.EMPTY_MESSAGE;
import static com.handwoong.rainbowletter.common.util.validation.ValidateMessage.PASSWORD_MESSAGE;

import com.handwoong.rainbowletter.member.domain.Email;
import com.handwoong.rainbowletter.member.domain.Password;
import com.handwoong.rainbowletter.member.domain.dto.MemberRegister;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record MemberRegisterRequest(
        @NotBlank(message = EMPTY_MESSAGE)
        @Pattern(regexp = Email.EMAIL_REGEX, message = EMAIL_MESSAGE)
        String email,

        @NotBlank(message = EMPTY_MESSAGE)
        @Pattern(regexp = Password.PASSWORD_REGEX, message = PASSWORD_MESSAGE)
        String password
) {
    public MemberRegister toDto() {
        return MemberRegister.builder()
                .email(new Email(email))
                .password(new Password(password))
                .build();
    }
}
