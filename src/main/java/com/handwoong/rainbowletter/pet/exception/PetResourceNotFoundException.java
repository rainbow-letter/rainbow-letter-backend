package com.handwoong.rainbowletter.pet.exception;

import com.handwoong.rainbowletter.common.exception.BaseException;
import com.handwoong.rainbowletter.common.exception.ErrorCode;
import lombok.Getter;

@Getter
public class PetResourceNotFoundException extends BaseException {
    private final Long id;

    public PetResourceNotFoundException(final Long id) {
        super(ErrorCode.NOT_FOUND_PET);
        this.id = id;
    }
}
