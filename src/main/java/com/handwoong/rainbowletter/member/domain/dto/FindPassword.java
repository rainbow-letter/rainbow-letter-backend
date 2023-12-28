package com.handwoong.rainbowletter.member.domain.dto;

import com.handwoong.rainbowletter.mail.domain.dto.EmailDto;
import com.handwoong.rainbowletter.member.domain.Email;

public record FindPassword(Email email) implements EmailDto {
}
