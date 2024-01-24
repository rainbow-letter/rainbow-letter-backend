package com.handwoong.rainbowletter.mail.infrastructure;

import com.handwoong.rainbowletter.common.util.ProfileManager;
import com.handwoong.rainbowletter.mail.domain.Mail;
import com.handwoong.rainbowletter.mail.service.port.MailSender;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import java.nio.charset.StandardCharsets;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MailSenderImpl implements MailSender {
    private final JavaMailSender javaMailSender;
    private final ProfileManager profileManager;

    @Override
    public void send(final Mail mail) throws MessagingException {
        final String currentProfile = profileManager.getActiveProfile();
        if (Objects.isNull(currentProfile) || !currentProfile.equals("prod")) {
            return;
        }
        final MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        final MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, true, StandardCharsets.UTF_8.name());
        messageHelper.setFrom("무지개 편지 <noreply@rainbowletter.co.kr>");
        messageHelper.setTo(mail.email().toString());
        messageHelper.setSubject(mail.title());
        messageHelper.setText(mail.content(), true);
        javaMailSender.send(mimeMessage);
    }
}
