package com.handwoong.rainbowletter.mail.domain;

import com.handwoong.rainbowletter.mail.domain.dto.MailTemplate;
import com.handwoong.rainbowletter.member.domain.Email;
import lombok.Builder;

@Builder
public record Mail(Long id, Email email, String title, String content, MailTemplateType templateType) {
    public static Mail create(final Email email, final MailTemplate template, final MailTemplateType templateType) {
        return Mail.builder()
                .email(email)
                .title(template.subject())
                .content(template.body())
                .templateType(templateType)
                .build();
    }
}
