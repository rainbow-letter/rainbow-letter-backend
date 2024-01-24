package com.handwoong.rainbowletter.favorite.infrastructure;

import com.handwoong.rainbowletter.common.infrastructure.BaseEntity;
import com.handwoong.rainbowletter.favorite.domain.Favorite;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.Objects;
import lombok.Getter;
import org.hibernate.Hibernate;

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

    @NotNull
    private LocalDateTime lastIncreaseAt;

    public static FavoriteEntity from(final Favorite favorite) {
        final FavoriteEntity favoriteEntity = new FavoriteEntity();
        favoriteEntity.id = favorite.id();
        favoriteEntity.total = favorite.total();
        favoriteEntity.dayIncreaseCount = favorite.dayIncreaseCount();
        favoriteEntity.canIncrease = favorite.canIncrease();
        favoriteEntity.lastIncreaseAt = favorite.lastIncreaseAt();
        return favoriteEntity;
    }

    public Favorite toModel() {
        return Favorite.builder()
                .id(id)
                .total(total)
                .dayIncreaseCount(dayIncreaseCount)
                .canIncrease(canIncrease)
                .lastIncreaseAt(lastIncreaseAt)
                .build();
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (Objects.isNull(obj) || Hibernate.getClass(this) != Hibernate.getClass(obj)) {
            return false;
        }
        FavoriteEntity entity = (FavoriteEntity) obj;
        return Objects.nonNull(id) && Objects.equals(id, entity.id);
    }

    @Override
    public int hashCode() {
        return Objects.nonNull(id) ? id.intValue() : 0;
    }
}
