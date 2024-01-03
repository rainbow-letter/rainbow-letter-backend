package com.handwoong.rainbowletter.member.exception;

import com.handwoong.rainbowletter.common.exception.BaseException;
import com.handwoong.rainbowletter.common.exception.ErrorCode;

public class UnAuthorizationException extends BaseException {
    public UnAuthorizationException() {
        super(ErrorCode.UN_AUTHORIZE);
    }
}
