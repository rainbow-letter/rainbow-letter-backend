package com.handwoong.rainbowletter.mail.domain;

import com.handwoong.rainbowletter.mail.domain.dto.MailTemplate;
import com.handwoong.rainbowletter.member.domain.Email;
import lombok.Builder;

@Builder
public record Mail(
        Long id,
        Email mail,
        String title,
        String content,
        EmailTemplateType templateType
) {
    public static Mail create(final Email email,
                              final MailTemplate template,
                              final EmailTemplateType templateType) {
        return Mail.builder()
                .email(email)
                .title(template.subject())
                .content(template.body())
                .templateType(templateType)
                .build();
    }
}
