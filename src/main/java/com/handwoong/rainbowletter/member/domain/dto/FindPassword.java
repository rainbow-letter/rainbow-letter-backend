package com.handwoong.rainbowletter.member.domain.dto;

import com.handwoong.rainbowletter.mail.domain.dto.MailDto;
import com.handwoong.rainbowletter.member.domain.Email;
import lombok.Builder;

@Builder
public record FindPassword(Email email) implements MailDto {
}
