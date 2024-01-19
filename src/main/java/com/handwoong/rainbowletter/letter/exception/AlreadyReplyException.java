package com.handwoong.rainbowletter.letter.exception;

import com.handwoong.rainbowletter.common.exception.BaseException;
import com.handwoong.rainbowletter.common.exception.ErrorCode;
import lombok.Getter;

@Getter
public class AlreadyReplyException extends BaseException {
    private final Long id;

    public AlreadyReplyException(final Long id) {
        super(ErrorCode.ALREADY_REPLY);
        this.id = id;
    }
}
