package com.handwoong.rainbowletter.image.exception;

import com.handwoong.rainbowletter.common.exception.BaseException;
import com.handwoong.rainbowletter.common.exception.ErrorCode;
import lombok.Getter;

@Getter
public class ImageResourceNotFoundException extends BaseException {
    private final Long id;

    public ImageResourceNotFoundException(final Long id) {
        super(ErrorCode.NOT_FOUND_IMAGE);
        this.id = id;
    }
}
