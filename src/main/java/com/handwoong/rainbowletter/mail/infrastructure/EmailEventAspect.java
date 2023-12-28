package com.handwoong.rainbowletter.mail.infrastructure;

import com.handwoong.rainbowletter.mail.domain.SendEmail;
import com.handwoong.rainbowletter.mail.domain.dto.EmailDto;
import com.handwoong.rainbowletter.mail.domain.dto.EmailEvent;
import com.handwoong.rainbowletter.member.domain.Email;
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
        eventPublisher.publishEvent(new EmailEvent(sendEmail.type(), new Email(returnValue.email())));
    }
}
