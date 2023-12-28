package com.handwoong.rainbowletter.mail.service.port;

import com.handwoong.rainbowletter.mail.domain.EmailTemplateType;
import com.handwoong.rainbowletter.mail.domain.dto.MailTemplate;
import com.handwoong.rainbowletter.member.domain.Email;

public interface EmailTemplateManager {
    MailTemplate template(Email email, EmailTemplateType type);
}
