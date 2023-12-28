package com.handwoong.rainbowletter.mail.infrastructure;

import com.handwoong.rainbowletter.mail.domain.dto.MailTemplate;
import com.handwoong.rainbowletter.mail.service.port.MailSender;
import com.handwoong.rainbowletter.member.domain.Email;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import java.nio.charset.StandardCharsets;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MailSenderImpl implements MailSender {
    private final JavaMailSender javaMailSender;

    @Override
    public void send(final Email email, final MailTemplate template) throws MessagingException {
        final MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        final MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, true, StandardCharsets.UTF_8.name());
        messageHelper.setFrom("무지개 편지 <noreply@rainbowletter.com>");
        messageHelper.setTo(email.toString());
        messageHelper.setSubject(template.subject());
        messageHelper.setText(template.body(), true);
        javaMailSender.send(mimeMessage);
    }
}
