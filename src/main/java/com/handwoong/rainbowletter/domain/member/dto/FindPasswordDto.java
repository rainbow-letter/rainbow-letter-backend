package com.handwoong.rainbowletter.domain.member.dto;

import static com.handwoong.rainbowletter.dto.ValidateMessage.EMAIL_MESSAGE;
import static com.handwoong.rainbowletter.dto.ValidateMessage.EMPTY_MESSAGE;

import com.handwoong.rainbowletter.dto.mail.EmailDto;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record FindPasswordDto(
        @NotBlank(message = EMPTY_MESSAGE)
        @Email(message = EMAIL_MESSAGE)
        String email
) implements EmailDto {
}
