package com.handwoong.rainbowletter.favorite.exception;

import com.handwoong.rainbowletter.common.exception.BaseException;
import com.handwoong.rainbowletter.common.exception.ErrorCode;

public class FavoriteIncreaseNotValidException extends BaseException {
    public FavoriteIncreaseNotValidException() {
        super(ErrorCode.INVALID_FAVORITE_INCREASE);
    }
}
