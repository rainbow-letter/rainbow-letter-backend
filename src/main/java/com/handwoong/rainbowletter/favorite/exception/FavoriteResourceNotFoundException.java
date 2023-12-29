package com.handwoong.rainbowletter.favorite.exception;

import com.handwoong.rainbowletter.common.exception.BaseException;
import com.handwoong.rainbowletter.common.exception.ErrorCode;
import lombok.Getter;

@Getter
public class FavoriteResourceNotFoundException extends BaseException {
    private final Long id;

    public FavoriteResourceNotFoundException(final Long id) {
        super(ErrorCode.NOT_FOUND_FAVORITE);
        this.id = id;
    }
}
