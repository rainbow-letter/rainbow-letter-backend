package com.handwoong.rainbowletter.favorite.controller.response;

import com.handwoong.rainbowletter.favorite.infrastructure.Favorite;

public record FavoriteResponse(
        Long id,
        int total,
        int dayIncreaseCount,
        boolean canIncrease
) {
    public static FavoriteResponse from(final Favorite favorite) {
        return new FavoriteResponse(
                favorite.getId(),
                favorite.getTotal(),
                favorite.getDayIncreaseCount(),
                favorite.isCanIncrease());
    }
}
