package com.handwoong.rainbowletter.mail.domain;

import com.handwoong.rainbowletter.mail.exception.MailTemplateNotFoundException;
import java.util.Arrays;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum MailTemplateType {
    VERIFY("emailVerifyTemplate"),
    FIND_PASSWORD("findPasswordTemplate");

    private final String templateName;

    public static MailTemplateType findTemplateTypeByName(final String templateName) {
        return Arrays.stream(MailTemplateType.values())
                .filter(templateType -> templateType.templateName.equals(templateName))
                .findAny()
                .orElseThrow(() -> new MailTemplateNotFoundException(templateName));
    }
}
