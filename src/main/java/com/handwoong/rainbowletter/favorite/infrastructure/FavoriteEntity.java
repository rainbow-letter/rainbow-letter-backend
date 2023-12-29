package com.handwoong.rainbowletter.favorite.infrastructure;

import com.handwoong.rainbowletter.common.infrastructure.BaseEntity;
import com.handwoong.rainbowletter.favorite.domain.Favorite;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
@Entity
@Table(name = "favorite")
public class FavoriteEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private int total;

    @NotNull
    private int dayIncreaseCount;

    @NotNull
    private boolean canIncrease;

    public static FavoriteEntity from(final Favorite favorite) {
        final FavoriteEntity favoriteEntity = new FavoriteEntity();
        favoriteEntity.id = favorite.id();
        favoriteEntity.total = favorite.total();
        favoriteEntity.dayIncreaseCount = favorite.dayIncreaseCount();
        favoriteEntity.canIncrease = favorite.canIncrease();
        return favoriteEntity;
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
}
