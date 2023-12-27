package com.handwoong.rainbowletter.mail.service.template;

import com.handwoong.rainbowletter.mail.dto.EmailTemplateDto;

public interface EmailTemplate {
    String SUBJECT_FORMAT = "[무지개 편지] %s";

    EmailTemplateDto getTemplate(final String email);
}
