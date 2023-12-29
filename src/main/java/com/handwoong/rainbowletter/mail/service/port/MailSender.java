package com.handwoong.rainbowletter.mail.service.port;

import com.handwoong.rainbowletter.mail.domain.Mail;
import jakarta.mail.MessagingException;

public interface MailSender {
    void send(Mail mail) throws MessagingException;
}
