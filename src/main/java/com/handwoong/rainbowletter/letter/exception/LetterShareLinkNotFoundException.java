package com.handwoong.rainbowletter.letter.exception;

import com.handwoong.rainbowletter.common.exception.BaseException;
import com.handwoong.rainbowletter.common.exception.ErrorCode;
import lombok.Getter;

@Getter
public class LetterShareLinkNotFoundException extends BaseException {
    private final String shareLink;

    public LetterShareLinkNotFoundException(final String shareLink) {
        super(ErrorCode.NOT_FOUND_LETTER);
        this.shareLink = shareLink;
    }
}
