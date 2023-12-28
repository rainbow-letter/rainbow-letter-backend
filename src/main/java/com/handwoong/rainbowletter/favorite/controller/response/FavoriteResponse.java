package com.handwoong.rainbowletter.favorite.controller.response;

import com.handwoong.rainbowletter.favorite.domain.Favorite;

public record FavoriteResponse(
        Long id,
        int total,
        int dayIncreaseCount,
        boolean canIncrease
) {
    public static FavoriteResponse from(final Favorite favorite) {
        return new FavoriteResponse(
                favorite.id(),
                favorite.total(),
                favorite.dayIncreaseCount(),
                favorite.canIncrease());
    }
}
