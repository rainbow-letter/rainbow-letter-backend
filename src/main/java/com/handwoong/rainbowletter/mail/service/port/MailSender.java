package com.handwoong.rainbowletter.mail.service.port;

import com.handwoong.rainbowletter.mail.domain.dto.MailTemplate;
import com.handwoong.rainbowletter.member.domain.Email;
import jakarta.mail.MessagingException;

public interface MailSender {
    void send(Email email, MailTemplate template) throws MessagingException;
}
