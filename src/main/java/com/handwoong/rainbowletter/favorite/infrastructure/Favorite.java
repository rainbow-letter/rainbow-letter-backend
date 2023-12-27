package com.handwoong.rainbowletter.favorite.infrastructure;

import com.handwoong.rainbowletter.common.infrastructure.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.time.LocalDate;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Favorite extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int total;

    private int dayIncreaseCount;

    private boolean canIncrease;

    private Favorite(final int total, final int dayIncreaseCount, final boolean canIncrease) {
        this.total = total;
        this.dayIncreaseCount = dayIncreaseCount;
        this.canIncrease = canIncrease;
    }

    public static Favorite create() {
        return new Favorite(0, 0, true);
    }

    public void increase() {
        if (possibleIncrease()) {
            this.total += 1;
            this.dayIncreaseCount += 1;
        }
    }

    private boolean possibleIncrease() {
        if (getUpdatedAt().isBefore(LocalDate.now().atStartOfDay())) {
            dayIncreaseCount = 0;
            canIncrease = true;
        }

        if (dayIncreaseCount >= 3) {
            canIncrease = false;
        }
        return canIncrease;
    }
}
