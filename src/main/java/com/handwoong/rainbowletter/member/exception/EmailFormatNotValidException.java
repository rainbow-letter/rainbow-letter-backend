package com.handwoong.rainbowletter.member.exception;

import com.handwoong.rainbowletter.common.exception.BaseException;
import com.handwoong.rainbowletter.common.exception.ErrorCode;
import lombok.Getter;

@Getter
public class EmailFormatNotValidException extends BaseException {
    private final String email;

    public EmailFormatNotValidException(final String email) {
        super(ErrorCode.INVALID_EMAIL_FORMAT);
        this.email = email;
    }
}
