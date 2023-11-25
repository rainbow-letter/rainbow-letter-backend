package com.handwoong.rainbowletter.service.mail.event;

import jakarta.mail.MessagingException;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;

import com.handwoong.rainbowletter.service.mail.EmailService;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class EmailEventListener {
    private final EmailService emailService;

    @Async
    @TransactionalEventListener
    public void handle(final EmailEvent event) throws MessagingException {
        emailService.send(event.email(), event.type());
    }
}
