package com.handwoong.rainbowletter.service.mail;

import java.nio.charset.StandardCharsets;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.handwoong.rainbowletter.dto.mail.EmailTemplateDto;
import com.handwoong.rainbowletter.service.mail.template.EmailTemplateManager;
import com.handwoong.rainbowletter.service.mail.template.EmailTemplateType;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {
    private final JavaMailSender mailSender;
    private final EmailTemplateManager templateManager;

    @Override
    public void send(final String email, final EmailTemplateType type) throws MessagingException {
        final MimeMessage mimeMessage = mailSender.createMimeMessage();
        final MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, true, StandardCharsets.UTF_8.name());
        messageHelper.setFrom("무지개 편지 <noreply@rainbowletter.com>");
        messageHelper.setTo(email);

        final EmailTemplateDto template = templateManager.template(email, type);
        messageHelper.setSubject(template.subject());
        messageHelper.setText(template.body(), true);
        mailSender.send(mimeMessage);
    }
}
