package com.handwoong.rainbowletter.exception;

import lombok.Getter;

@Getter
public class RainbowLetterException extends BaseException {
    private final String field;

    public RainbowLetterException(final ErrorCode errorCode) {
        super(errorCode);
        this.field = "";
    }

    public RainbowLetterException(final ErrorCode errorCode, final String field) {
        super(errorCode);
        this.field = field;
    }
}
