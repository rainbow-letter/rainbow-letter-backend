package com.handwoong.rainbowletter.service.mail.template;

import static com.handwoong.rainbowletter.service.mail.template.EmailTemplateType.VERIFY;

import java.util.EnumMap;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.handwoong.rainbowletter.dto.mail.EmailTemplateDto;

@Component
public class EmailTemplateManager {
    private final Map<EmailTemplateType, EmailTemplate> templates = new EnumMap<>(EmailTemplateType.class);

    public EmailTemplateManager(final EmailVerifyTemplate verifyTemplate) {
        templates.put(VERIFY, verifyTemplate);
    }

    public EmailTemplateDto template(final String email, final EmailTemplateType type) {
        final EmailTemplate emailTemplate = templates.get(type);
        return emailTemplate.getTemplate(email);
    }
}
