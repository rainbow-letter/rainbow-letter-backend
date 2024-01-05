package com.handwoong.rainbowletter.pet.exception;

import com.handwoong.rainbowletter.common.exception.BaseException;
import com.handwoong.rainbowletter.common.exception.ErrorCode;
import lombok.Getter;

@Getter
public class PersonalityFormatNotValidException extends BaseException {
    private final String personality;

    public PersonalityFormatNotValidException(final String personality) {
        super(ErrorCode.INVALID_PERSONALITY_FORMAT);
        this.personality = personality;
    }
}
