package com.handwoong.rainbowletter.member.exception;

import com.handwoong.rainbowletter.common.exception.BaseException;
import com.handwoong.rainbowletter.common.exception.ErrorCode;

public class PasswordNotMatchedException extends BaseException {
    public PasswordNotMatchedException() {
        super(ErrorCode.FAIL_PASSWORD_COMPARE);
    }
}
