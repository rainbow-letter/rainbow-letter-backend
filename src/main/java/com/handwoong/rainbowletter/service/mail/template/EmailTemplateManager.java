package com.handwoong.rainbowletter.service.mail.template;

import static com.handwoong.rainbowletter.service.mail.template.EmailTemplateType.FIND_PASSWORD;
import static com.handwoong.rainbowletter.service.mail.template.EmailTemplateType.VERIFY;

import com.handwoong.rainbowletter.dto.mail.EmailTemplateDto;
import java.util.EnumMap;
import java.util.Map;
import org.springframework.stereotype.Component;

@Component
public class EmailTemplateManager {
    private final Map<EmailTemplateType, EmailTemplate> templates = new EnumMap<>(EmailTemplateType.class);

    public EmailTemplateManager(final EmailVerifyTemplate verifyTemplate,
                                final FindPasswordTemplate findPasswordTemplate) {
        templates.put(VERIFY, verifyTemplate);
        templates.put(FIND_PASSWORD, findPasswordTemplate);
    }

    public EmailTemplateDto template(final String email, final EmailTemplateType type) {
        final EmailTemplate emailTemplate = templates.get(type);
        return emailTemplate.getTemplate(email);
    }
}
