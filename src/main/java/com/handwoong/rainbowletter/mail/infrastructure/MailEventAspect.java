package com.handwoong.rainbowletter.mail.infrastructure;

import com.handwoong.rainbowletter.mail.domain.SendMail;
import com.handwoong.rainbowletter.mail.domain.dto.MailDto;
import com.handwoong.rainbowletter.mail.domain.dto.MailEvent;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Profile("!test")
@Aspect
@Component
@RequiredArgsConstructor
public class MailEventAspect {
    private final ApplicationEventPublisher eventPublisher;

    @Pointcut("@annotation(sendMail)")
    public void pointcut(final SendMail sendMail) {
    }

    @AfterReturning(value = "pointcut(sendMail)", returning = "returnValue", argNames = "sendMail,returnValue")
    public void afterReturning(final SendMail sendMail, final MailDto returnValue) {
        eventPublisher.publishEvent(new MailEvent(sendMail.type(), returnValue.email(), returnValue.url()));
    }
}
