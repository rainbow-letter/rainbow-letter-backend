package com.handwoong.rainbowletter.mail.domain.dto;

import com.handwoong.rainbowletter.member.domain.Email;
import lombok.Builder;

@Builder
public record MailDto(Email email, String subject, String url) {
}
