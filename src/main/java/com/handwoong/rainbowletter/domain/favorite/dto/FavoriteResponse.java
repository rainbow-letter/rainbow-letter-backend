package com.handwoong.rainbowletter.domain.favorite.dto;

import com.handwoong.rainbowletter.domain.favorite.model.Favorite;

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
