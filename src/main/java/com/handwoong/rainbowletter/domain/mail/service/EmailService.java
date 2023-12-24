package com.handwoong.rainbowletter.domain.mail.service;

import com.handwoong.rainbowletter.domain.mail.service.template.EmailTemplateType;
import jakarta.mail.MessagingException;

public interface EmailService {
    void send(final String email, final EmailTemplateType type) throws MessagingException;
}
