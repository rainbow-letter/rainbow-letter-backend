package com.handwoong.rainbowletter.common.exception;

public class JwtTokenInvalidException extends BaseException {
    public JwtTokenInvalidException() {
        super(ErrorCode.INVALID_TOKEN);
    }

    public JwtTokenInvalidException(final Throwable cause) {
        super(ErrorCode.INVALID_TOKEN, cause);
    }
}
