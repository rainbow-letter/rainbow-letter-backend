package com.handwoong.rainbowletter.faq.exception;

import com.handwoong.rainbowletter.common.exception.BaseException;
import com.handwoong.rainbowletter.common.exception.ErrorCode;
import lombok.Getter;

@Getter
public class FAQResourceNotFoundException extends BaseException {
    private final Long id;

    public FAQResourceNotFoundException(final Long id) {
        super(ErrorCode.NOT_FOUND_FAQ);
        this.id = id;
    }
}
