package com.handwoong.rainbowletter.letter.exception;

import com.handwoong.rainbowletter.common.exception.BaseException;
import com.handwoong.rainbowletter.common.exception.ErrorCode;
import lombok.Getter;

@Getter
public class ContentFormatNotValidException extends BaseException {
    private final String content;

    public ContentFormatNotValidException(final String content) {
        super(ErrorCode.INVALID_CONTENT_FORMAT);
        this.content = content;
    }
}
