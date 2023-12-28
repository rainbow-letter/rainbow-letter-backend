package com.handwoong.rainbowletter.member.exception;

import com.handwoong.rainbowletter.common.exception.BaseException;
import com.handwoong.rainbowletter.common.exception.ErrorCode;
import lombok.Getter;

@Getter
public class PhoneNumberFormatNotValidException extends BaseException {
    private final String phoneNumber;

    public PhoneNumberFormatNotValidException(final String phoneNumber) {
        super(ErrorCode.INVALID_PHONE_NUMBER_FORMAT);
        this.phoneNumber = phoneNumber;
    }
}
