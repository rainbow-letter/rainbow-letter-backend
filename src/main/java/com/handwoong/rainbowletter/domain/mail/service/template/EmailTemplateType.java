package com.handwoong.rainbowletter.domain.mail.service.template;

import java.util.Arrays;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum EmailTemplateType {
    VERIFY("emailVerifyTemplate"),
    FIND_PASSWORD("findPasswordTemplate");

    private final String templateName;

    public static EmailTemplateType findTemplateTypeByName(final String templateName) {
        return Arrays.stream(EmailTemplateType.values())
                .filter(templateType -> templateType.templateName.equals(templateName))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException(templateName + " 에 해당하는 일치하는 이메일 템플릿이 없습니다."));
    }
}
