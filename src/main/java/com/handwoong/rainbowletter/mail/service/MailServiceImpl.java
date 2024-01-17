package com.handwoong.rainbowletter.mail.service;

import com.handwoong.rainbowletter.mail.domain.Mail;
import com.handwoong.rainbowletter.mail.domain.MailTemplateType;
import com.handwoong.rainbowletter.mail.domain.dto.MailDto;
import com.handwoong.rainbowletter.mail.domain.dto.MailTemplate;
import com.handwoong.rainbowletter.mail.exception.MailSendFailException;
import com.handwoong.rainbowletter.mail.service.port.EmailTemplateManager;
import com.handwoong.rainbowletter.mail.service.port.MailRepository;
import com.handwoong.rainbowletter.mail.service.port.MailSender;
import com.handwoong.rainbowletter.mail.service.port.MailService;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MailServiceImpl implements MailService {
    private final MailSender mailSender;
    private final EmailTemplateManager templateManager;
    private final MailRepository mailRepository;

    @Override
    @Async
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void send(final MailTemplateType type, final MailDto mailDto) {
        try {
            final MailTemplate template = templateManager.template(
                    mailDto.email(), type, mailDto.subject(), mailDto.url());
            final Mail mail = Mail.create(mailDto.email(), template, type);
            mailSender.send(mail);
            mailRepository.save(mail);
        } catch (MessagingException exception) {
            throw new MailSendFailException(type, mailDto.email().toString());
        }
    }
}
