package com.handwoong.rainbowletter.mail.domain;

import com.handwoong.rainbowletter.mail.exception.MailTemplateNotFoundException;
import java.util.Arrays;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum MailTemplateType {
    VERIFY("emailVerifyTemplate"),
    FIND_PASSWORD("findPasswordTemplate"),
    REPLY("replyLetterTemplate"),
    ;

    private final String templateName;

    public static MailTemplateType findTemplateTypeByName(final String templateName) {
        return Arrays.stream(MailTemplateType.values())
                .filter(templateType -> templateType.templateName.equals(templateName))
                .findAny()
                .orElseThrow(() -> new MailTemplateNotFoundException(templateName));
    }
}
