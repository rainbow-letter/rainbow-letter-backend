package com.handwoong.rainbowletter.service.mail.template;

import com.handwoong.rainbowletter.dto.mail.EmailTemplateDto;

public interface EmailTemplate {
    String SUBJECT_FORMAT = "[무지개 편지] %s";

    EmailTemplateDto getTemplate(final String email);
}
