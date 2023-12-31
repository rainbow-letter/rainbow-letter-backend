package com.handwoong.rainbowletter.mock;

import com.handwoong.rainbowletter.mail.domain.MailTemplateType;
import com.handwoong.rainbowletter.mail.domain.dto.MailTemplate;
import com.handwoong.rainbowletter.mail.service.port.EmailTemplateManager;
import com.handwoong.rainbowletter.member.domain.Email;

public class FakeMailTemplateManager implements EmailTemplateManager {
    private final String subject;
    private final String body;

    public FakeMailTemplateManager(final String subject, final String body) {
        this.subject = subject;
        this.body = body;
    }

    @Override
    public MailTemplate template(final Email email, final MailTemplateType type) {
        return new MailTemplate(subject, body);
    }
}
