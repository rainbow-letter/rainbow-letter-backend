package com.handwoong.rainbowletter.mail.service.event;

import com.handwoong.rainbowletter.mail.service.template.EmailTemplateType;

public record EmailEvent(
        EmailTemplateType type,
        String email
) {
}
