package com.handwoong.rainbowletter.mail.exception;

import com.handwoong.rainbowletter.common.exception.BaseException;
import com.handwoong.rainbowletter.common.exception.ErrorCode;
import com.handwoong.rainbowletter.mail.domain.MailTemplateType;
import lombok.Getter;

@Getter
public class MailSendFailException extends BaseException {
    private final MailTemplateType type;
    private final String email;

    public MailSendFailException(final MailTemplateType type, final String email) {
        super(ErrorCode.FAIL_SEND_MAIL);
        this.type = type;
        this.email = email;
    }
}
