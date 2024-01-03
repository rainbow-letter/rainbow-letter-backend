package com.handwoong.rainbowletter.member.exception;

import com.handwoong.rainbowletter.common.exception.BaseException;
import com.handwoong.rainbowletter.common.exception.ErrorCode;
import lombok.Getter;

@Getter
public class MemberStatusNotValidException extends BaseException {
    private final String status;

    public MemberStatusNotValidException(final String status) {
        super(ErrorCode.INVALID_MEMBER_STATUS);
        this.status = status;
    }
}
