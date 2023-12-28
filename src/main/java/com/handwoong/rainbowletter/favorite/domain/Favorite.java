package com.handwoong.rainbowletter.favorite.domain;

import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.Builder;

@Builder
public record Favorite(
        Long id,
        int total,
        int dayIncreaseCount,
        boolean canIncrease,
        LocalDateTime updatedAt
) {
    public Favorite checkRequireInitDayCount() {
        if (updatedAt.isBefore(LocalDate.now().atStartOfDay())) {
            return Favorite.builder()
                    .id(id)
                    .total(total)
                    .dayIncreaseCount(0)
                    .canIncrease(true)
                    .updatedAt(updatedAt)
                    .build();
        }
        return this;
    }

    public Favorite increase() {
        if (dayIncreaseCount >= 3) {
            return Favorite.builder()
                    .id(id)
                    .total(total)
                    .dayIncreaseCount(dayIncreaseCount)
                    .canIncrease(false)
                    .updatedAt(updatedAt)
                    .build();
        }
        return Favorite.builder()
                .id(id)
                .total(total + 1)
                .dayIncreaseCount(dayIncreaseCount + 1)
                .canIncrease(canIncrease)
                .updatedAt(updatedAt)
                .build();
    }
}
