package com.handwoong.rainbowletter.mail.domain.dto;

import com.handwoong.rainbowletter.mail.domain.EmailTemplateType;
import com.handwoong.rainbowletter.member.domain.Email;

public record EmailEvent(EmailTemplateType type, Email email) {
}
