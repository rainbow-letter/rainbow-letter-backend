package com.handwoong.rainbowletter.service.mail.event;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.handwoong.rainbowletter.service.mail.template.EmailTemplateType;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface SendEmail {
    EmailTemplateType type();
}
