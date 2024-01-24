package com.handwoong.rainbowletter.member.exception;

import com.handwoong.rainbowletter.common.exception.BaseException;
import com.handwoong.rainbowletter.common.exception.ErrorCode;
import lombok.Getter;

@Getter
public class DuplicateEmailException extends BaseException {
    private final String email;

    public DuplicateEmailException(final String email) {
        super(ErrorCode.EXISTS_EMAIL);
        this.email = email;
    }
}
