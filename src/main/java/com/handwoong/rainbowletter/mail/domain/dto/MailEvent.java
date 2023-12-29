package com.handwoong.rainbowletter.mail.domain.dto;

import com.handwoong.rainbowletter.mail.domain.MailTemplateType;
import com.handwoong.rainbowletter.member.domain.Email;

public record MailEvent(MailTemplateType type, Email email) {
}
