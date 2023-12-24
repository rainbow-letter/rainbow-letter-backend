package com.handwoong.rainbowletter.domain.mail.service.event;

import com.handwoong.rainbowletter.domain.mail.service.template.EmailTemplateType;

public record EmailEvent(
        EmailTemplateType type,
        String email
) {
}
