package com.handwoong.rainbowletter.favorite.domain;

import com.handwoong.rainbowletter.favorite.exception.FavoriteIncreaseNotValidException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.Builder;

@Builder
public record Favorite(Long id, int total, int dayIncreaseCount, boolean canIncrease, LocalDateTime lastIncreaseAt) {
    public static Favorite create() {
        return Favorite.builder()
                .total(0)
                .dayIncreaseCount(0)
                .canIncrease(true)
                .lastIncreaseAt(LocalDateTime.now())
                .build();
    }

    public Favorite resetDayIncreaseCount() {
        if (lastIncreaseAt.isBefore(LocalDate.now().atStartOfDay())) {
            return resetCount(total, 0);
        }
        return this;
    }

    public Favorite increase() {
        if (!canIncreaseCount(dayIncreaseCount)) {
            throw new FavoriteIncreaseNotValidException();
        }
        return resetCount(total + 1, dayIncreaseCount + 1);
    }

    private boolean canIncreaseCount(int dayIncreaseCount) {
        return dayIncreaseCount < 3;
    }

    private Favorite resetCount(final int total, final int dayIncreaseCount) {
        return Favorite.builder()
                .id(id)
                .total(total)
                .dayIncreaseCount(dayIncreaseCount)
                .canIncrease(canIncreaseCount(dayIncreaseCount))
                .lastIncreaseAt(LocalDateTime.now())
                .build();
    }
}
