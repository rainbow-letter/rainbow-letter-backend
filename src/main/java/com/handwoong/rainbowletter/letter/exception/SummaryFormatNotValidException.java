package com.handwoong.rainbowletter.letter.exception;

import com.handwoong.rainbowletter.common.exception.BaseException;
import com.handwoong.rainbowletter.common.exception.ErrorCode;
import lombok.Getter;

@Getter
public class SummaryFormatNotValidException extends BaseException {
    private final String summary;

    public SummaryFormatNotValidException(final String summary) {
        super(ErrorCode.INVALID_SUMMARY_FORMAT);
        this.summary = summary;
    }
}
