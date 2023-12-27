package com.handwoong.rainbowletter.mail.service.event;

import com.handwoong.rainbowletter.mail.service.template.EmailTemplateType;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface SendEmail {
    EmailTemplateType type();
}
