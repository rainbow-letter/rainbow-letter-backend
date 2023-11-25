package com.handwoong.rainbowletter.service.mail.event;

import com.handwoong.rainbowletter.service.mail.template.EmailTemplateType;

public record EmailEvent(
        EmailTemplateType type,
        String email
) {
}
