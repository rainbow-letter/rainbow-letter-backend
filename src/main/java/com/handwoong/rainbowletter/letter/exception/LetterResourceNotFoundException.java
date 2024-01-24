package com.handwoong.rainbowletter.letter.exception;

import com.handwoong.rainbowletter.common.exception.BaseException;
import com.handwoong.rainbowletter.common.exception.ErrorCode;
import lombok.Getter;

@Getter
public class LetterResourceNotFoundException extends BaseException {
    private final Long id;

    public LetterResourceNotFoundException(final Long id) {
        super(ErrorCode.NOT_FOUND_LETTER);
        this.id = id;
    }
}
