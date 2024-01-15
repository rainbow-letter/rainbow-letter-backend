package com.handwoong.rainbowletter.mail.service;

import com.handwoong.rainbowletter.mail.domain.Mail;
import com.handwoong.rainbowletter.mail.domain.MailTemplateType;
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
public class MailServiceImpl implements MailService {
    private final MailSender mailSender;
    private final EmailTemplateManager templateManager;
    private final MailRepository mailRepository;

    @Override
    public Mail send(final Email email,
                     final MailTemplateType type,
                     final String subject,
                     final String url) throws MessagingException {
        final MailTemplate template = templateManager.template(email, type, subject, url);
        final Mail mail = Mail.create(email, template, type);
        mailSender.send(mail);
        return mailRepository.save(mail);
    }
}
