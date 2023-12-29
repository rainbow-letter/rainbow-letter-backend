package com.handwoong.rainbowletter.favorite.controller.response;

import com.handwoong.rainbowletter.favorite.domain.Favorite;
import lombok.Builder;

@Builder
public record FavoriteResponse(Long id, int total, int dayIncreaseCount, boolean canIncrease) {
    public static FavoriteResponse from(final Favorite favorite) {
        return FavoriteResponse.builder()
                .id(favorite.id())
                .total(favorite.total())
                .dayIncreaseCount(favorite.dayIncreaseCount())
                .canIncrease(favorite.canIncrease())
                .build();
    }
}
