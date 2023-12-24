package com.handwoong.rainbowletter.domain.mail.service.event;

import com.handwoong.rainbowletter.domain.mail.service.EmailService;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;

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
