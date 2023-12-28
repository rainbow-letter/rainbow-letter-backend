package com.handwoong.rainbowletter.favorite.exception;

import com.handwoong.rainbowletter.common.exception.BaseException;
import lombok.Getter;

@Getter
public class FavoriteResourceNotFoundException extends BaseException {
    private final Long id;

    public FavoriteResourceNotFoundException(final Long id) {
        super();
        this.id = id;
    }
}
