package com.handwoong.rainbowletter.image.exception;

import com.handwoong.rainbowletter.common.exception.BaseException;
import com.handwoong.rainbowletter.common.exception.ErrorCode;
import lombok.Getter;

@Getter
public class ImageUploadFailException extends BaseException {
    public ImageUploadFailException() {
        super(ErrorCode.FAIL_UPLOAD_IMAGE);
    }
}
