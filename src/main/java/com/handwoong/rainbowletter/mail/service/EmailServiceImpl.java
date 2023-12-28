package com.handwoong.rainbowletter.mail.service;

import com.handwoong.rainbowletter.mail.domain.EmailTemplateType;
import com.handwoong.rainbowletter.mail.domain.Mail;
import com.handwoong.rainbowletter.mail.domain.dto.MailTemplate;
import com.handwoong.rainbowletter.mail.service.port.EmailTemplateManager;
import com.handwoong.rainbowletter.mail.service.port.MailRepository;
import com.handwoong.rainbowletter.mail.service.port.MailSender;
import com.handwoong.rainbowletter.member.domain.Email;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {
    private final MailSender mailSender;
    private final EmailTemplateManager templateManager;
    private final MailRepository mailRepository;

    @Override
    public void send(final Email email, final EmailTemplateType type) throws MessagingException {
        final MailTemplate template = templateManager.template(email, type);
        mailSender.send(email, template);
        mailRepository.save(Mail.create(email, template, type));
    }
}
