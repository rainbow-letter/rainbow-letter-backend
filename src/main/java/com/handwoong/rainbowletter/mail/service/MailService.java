package com.handwoong.rainbowletter.mail.service;

import com.handwoong.rainbowletter.mail.domain.Mail;
import com.handwoong.rainbowletter.mail.domain.MailTemplateType;
import com.handwoong.rainbowletter.member.domain.Email;
import jakarta.mail.MessagingException;

public interface MailService {
    Mail send(final Email email, final MailTemplateType type) throws MessagingException;
}
