package com.handwoong.rainbowletter.mail.infrastructure.template;

import com.handwoong.rainbowletter.mail.domain.dto.MailTemplate;
import com.handwoong.rainbowletter.member.domain.Email;

public interface EmailTemplate {
    String SUBJECT_FORMAT = "[무지개 편지] %s";

    MailTemplate getTemplate(final Email email);
}
