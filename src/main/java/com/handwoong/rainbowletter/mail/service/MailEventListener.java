package com.handwoong.rainbowletter.mail.service;

import com.handwoong.rainbowletter.mail.domain.dto.MailEvent;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
@RequiredArgsConstructor
public class MailEventListener {
    private final MailService mailService;

    @Async
    @TransactionalEventListener
    public void handle(final MailEvent event) throws MessagingException {
        mailService.send(event.email(), event.type(), event.url());
    }
}
