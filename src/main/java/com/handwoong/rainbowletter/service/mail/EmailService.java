package com.handwoong.rainbowletter.service.mail;

import jakarta.mail.MessagingException;

import com.handwoong.rainbowletter.service.mail.template.EmailTemplateType;

public interface EmailService {
    void send(final String email, final EmailTemplateType type) throws MessagingException;
}
