package com.handwoong.rainbowletter.favorite.infrastructure;

import com.handwoong.rainbowletter.common.infrastructure.BaseEntity;
import com.handwoong.rainbowletter.favorite.domain.Favorite;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDate;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "favorite")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class FavoriteEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int total;

    private int dayIncreaseCount;

    private boolean canIncrease;

    private FavoriteEntity(final int total, final int dayIncreaseCount, final boolean canIncrease) {
        this.total = total;
        this.dayIncreaseCount = dayIncreaseCount;
        this.canIncrease = canIncrease;
    }

    public static FavoriteEntity create() {
        return new FavoriteEntity(0, 0, true);
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

    public Favorite toModel() {
        return Favorite.builder()
                .id(id)
                .total(total)
                .dayIncreaseCount(dayIncreaseCount)
                .canIncrease(canIncrease)
                .updatedAt(getUpdatedAt())
                .build();
    }

    public static FavoriteEntity fromModel(final Favorite favorite) {
        final FavoriteEntity favoriteEntity = new FavoriteEntity();
        favoriteEntity.id = favorite.id();
        favoriteEntity.total = favorite.total();
        favoriteEntity.dayIncreaseCount = favorite.dayIncreaseCount();
        favoriteEntity.canIncrease = favorite.canIncrease();
        return favoriteEntity;
    }
}
