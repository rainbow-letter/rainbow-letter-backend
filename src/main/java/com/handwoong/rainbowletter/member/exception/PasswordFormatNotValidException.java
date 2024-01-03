package com.handwoong.rainbowletter.member.exception;

import com.handwoong.rainbowletter.common.exception.BaseException;
import com.handwoong.rainbowletter.common.exception.ErrorCode;
import lombok.Getter;

@Getter
public class PasswordFormatNotValidException extends BaseException {
    public PasswordFormatNotValidException() {
        super(ErrorCode.INVALID_PASSWORD_FORMAT);
    }
}
