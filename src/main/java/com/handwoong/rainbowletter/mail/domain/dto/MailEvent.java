package com.handwoong.rainbowletter.mail.domain.dto;

import com.handwoong.rainbowletter.mail.domain.MailTemplateType;
import com.handwoong.rainbowletter.member.domain.Email;
import lombok.Builder;

@Builder
public record MailEvent(MailTemplateType type, Email email, String subject, String url) {
}
