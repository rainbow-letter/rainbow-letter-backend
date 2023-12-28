package com.handwoong.rainbowletter.mail.service;

import com.handwoong.rainbowletter.mail.domain.EmailTemplateType;
import com.handwoong.rainbowletter.member.domain.Email;
import jakarta.mail.MessagingException;

public interface EmailService {
    void send(final Email email, final EmailTemplateType type) throws MessagingException;
}
