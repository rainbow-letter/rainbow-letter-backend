package com.handwoong.rainbowletter.letter.exception;

import com.handwoong.rainbowletter.common.exception.BaseException;
import com.handwoong.rainbowletter.common.exception.ErrorCode;
import lombok.Getter;

@Getter
public class ReplyResourceNotFoundException extends BaseException {
    private final Long id;

    public ReplyResourceNotFoundException(final Long id) {
        super(ErrorCode.NOT_FOUND_REPLY);
        this.id = id;
    }
}
