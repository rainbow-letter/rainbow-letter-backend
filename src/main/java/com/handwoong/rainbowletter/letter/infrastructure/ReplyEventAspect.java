package com.handwoong.rainbowletter.letter.infrastructure;

import com.handwoong.rainbowletter.letter.domain.CreateReply;
import com.handwoong.rainbowletter.letter.domain.Letter;
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
public class ReplyEventAspect {
    private final ApplicationEventPublisher eventPublisher;

    @Pointcut("@annotation(createReply)")
    public void pointcut(final CreateReply createReply) {
    }

    @AfterReturning(value = "pointcut(createReply)", returning = "returnValue", argNames = "createReply,returnValue")
    public void afterReturning(final CreateReply createReply, final Letter returnValue) {
        eventPublisher.publishEvent(returnValue);
    }
}
