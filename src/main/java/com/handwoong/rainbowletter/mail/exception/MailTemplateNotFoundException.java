package com.handwoong.rainbowletter.mail.exception;

import com.handwoong.rainbowletter.common.exception.BaseException;
import com.handwoong.rainbowletter.common.exception.ErrorCode;
import lombok.Getter;

@Getter
public class MailTemplateNotFoundException extends BaseException {
    private final String templateName;

    public MailTemplateNotFoundException(final String templateName) {
        super(ErrorCode.MAIL_TEMPLATE_NOT_FOUND);
        this.templateName = templateName;
    }
}
