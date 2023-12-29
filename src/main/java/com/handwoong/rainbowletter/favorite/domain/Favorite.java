package com.handwoong.rainbowletter.favorite.domain;

import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.Builder;

@Builder
public record Favorite(Long id, int total, int dayIncreaseCount, boolean canIncrease, LocalDateTime updatedAt) {
    public static Favorite create() {
        return Favorite.builder()
                .total(0)
                .dayIncreaseCount(0)
                .canIncrease(true)
                .build();
    }

    public Favorite initDayIncreaseCount() {
        if (updatedAt.isBefore(LocalDate.now().atStartOfDay())) {
            return createIncreaseFavorite(total, 0, true);
        }
        return this;
    }

    public Favorite increase() {
        if (dayIncreaseCount >= 3) {
            return createIncreaseFavorite(total, dayIncreaseCount, false);
        }
        return createIncreaseFavorite(total + 1, dayIncreaseCount + 1, canIncrease);
    }

    private Favorite createIncreaseFavorite(final int total, final int dayIncreaseCount, final boolean canIncrease) {
        return Favorite.builder()
                .id(id)
                .total(total)
                .dayIncreaseCount(dayIncreaseCount)
                .canIncrease(canIncrease)
                .updatedAt(updatedAt)
                .build();
    }
}
