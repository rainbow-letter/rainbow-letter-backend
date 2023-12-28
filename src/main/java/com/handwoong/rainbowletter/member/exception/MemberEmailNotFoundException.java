package com.handwoong.rainbowletter.member.exception;

import com.handwoong.rainbowletter.common.exception.BaseException;
import com.handwoong.rainbowletter.common.exception.ErrorCode;
import lombok.Getter;

@Getter
public class MemberEmailNotFoundException extends BaseException {
    private final String email;

    public MemberEmailNotFoundException(final String email) {
        super(ErrorCode.NOT_FOUND_MEMBER);
        this.email = email;
    }
}
