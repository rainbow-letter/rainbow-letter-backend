package com.handwoong.rainbowletter.mail.service.event;

import com.handwoong.rainbowletter.mail.dto.EmailDto;
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
public class EmailEventAspect {
    private final ApplicationEventPublisher eventPublisher;

    @Pointcut("@annotation(sendEmail)")
    public void pointcut(final SendEmail sendEmail) {
    }

    @AfterReturning(value = "pointcut(sendEmail)", returning = "returnValue", argNames = "sendEmail,returnValue")
    public void afterReturning(final SendEmail sendEmail, final EmailDto returnValue) {
        eventPublisher.publishEvent(new EmailEvent(sendEmail.type(), returnValue.email()));
    }
}
