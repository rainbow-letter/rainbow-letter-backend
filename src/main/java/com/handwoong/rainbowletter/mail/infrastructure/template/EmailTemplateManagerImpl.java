package com.handwoong.rainbowletter.mail.infrastructure.template;

import com.handwoong.rainbowletter.mail.domain.EmailTemplateType;
import com.handwoong.rainbowletter.mail.domain.dto.MailTemplate;
import com.handwoong.rainbowletter.mail.service.port.EmailTemplateManager;
import com.handwoong.rainbowletter.member.domain.Email;
import java.util.EnumMap;
import java.util.Map;
import java.util.Map.Entry;
import org.springframework.stereotype.Component;

@Component
public class EmailTemplateManagerImpl implements EmailTemplateManager {
    private final Map<EmailTemplateType, EmailTemplate> templates = new EnumMap<>(EmailTemplateType.class);

    public EmailTemplateManagerImpl(final Map<String, EmailTemplate> emailTemplates) {
        for (final Entry<String, EmailTemplate> emailTemplate : emailTemplates.entrySet()) {
            final String templateName = emailTemplate.getKey();
            final EmailTemplate template = emailTemplate.getValue();
            final EmailTemplateType templateType = EmailTemplateType.findTemplateTypeByName(templateName);
            templates.put(templateType, template);
        }
    }

    public MailTemplate template(final Email email, final EmailTemplateType type) {
        final EmailTemplate emailTemplate = templates.get(type);
        return emailTemplate.getTemplate(email);
    }
}
