package com.handwoong.rainbowletter.mock;

import com.handwoong.rainbowletter.mail.domain.Mail;
import com.handwoong.rainbowletter.mail.domain.MailTemplateType;
import com.handwoong.rainbowletter.mail.service.port.MailSender;
import com.handwoong.rainbowletter.member.domain.Email;

public class FakeMailSender implements MailSender {
    public Long id;
    public Email email;
    public String title;
    public String content;
    public MailTemplateType type;

    @Override
    public void send(final Mail mail) {
        this.id = mail.id();
        this.email = mail.email();
        this.title = mail.title();
        this.content = mail.content();
        this.type = mail.templateType();
    }
}
