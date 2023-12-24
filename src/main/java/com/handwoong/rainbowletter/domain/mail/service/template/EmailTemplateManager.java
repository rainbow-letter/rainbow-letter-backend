package com.handwoong.rainbowletter.domain.mail.service.template;

import com.handwoong.rainbowletter.domain.mail.dto.EmailTemplateDto;
import java.util.EnumMap;
import java.util.Map;
import java.util.Map.Entry;
import org.springframework.stereotype.Component;

@Component
public class EmailTemplateManager {
    private final Map<EmailTemplateType, EmailTemplate> templates = new EnumMap<>(EmailTemplateType.class);

    public EmailTemplateManager(final Map<String, EmailTemplate> emailTemplates) {
        for (final Entry<String, EmailTemplate> emailTemplate : emailTemplates.entrySet()) {
            final String templateName = emailTemplate.getKey();
            final EmailTemplate template = emailTemplate.getValue();
            final EmailTemplateType templateType = EmailTemplateType.findTemplateTypeByName(templateName);
            templates.put(templateType, template);
        }
    }

    public EmailTemplateDto template(final String email, final EmailTemplateType type) {
        final EmailTemplate emailTemplate = templates.get(type);
        return emailTemplate.getTemplate(email);
    }
}
