package com.handwoong.rainbowletter.favorite.infrastructure;

import static org.assertj.core.api.Assertions.assertThat;

import com.handwoong.rainbowletter.favorite.domain.Favorite;
import java.time.LocalDateTime;
import org.junit.jupiter.api.Test;

class FavoriteEntityTest {
    @Test
    void 좋아요_도메인으로_엔티티를_생성한다() {
        // given
        final LocalDateTime currentTime = LocalDateTime.now();
        final Favorite favorite = Favorite.builder()
                .id(1L)
                .total(10)
                .dayIncreaseCount(2)
                .canIncrease(true)
                .lastIncreaseAt(currentTime)
                .build();

        // when
        final FavoriteEntity favoriteEntity = FavoriteEntity.from(favorite);

        // then
        assertThat(favoriteEntity.getId()).isEqualTo(1);
        assertThat(favoriteEntity.getTotal()).isEqualTo(10);
        assertThat(favoriteEntity.getDayIncreaseCount()).isEqualTo(2);
        assertThat(favoriteEntity.isCanIncrease()).isTrue();
        assertThat(favoriteEntity.getLastIncreaseAt()).isEqualTo(currentTime);
    }

    @Test
    void 엔티티로_좋아요_도메인을_생성한다() {
        // given
        final LocalDateTime currentTime = LocalDateTime.now();
        final Favorite favorite = Favorite.builder()
                .id(1L)
                .total(10)
                .dayIncreaseCount(2)
                .canIncrease(true)
                .lastIncreaseAt(currentTime)
                .build();

        final FavoriteEntity favoriteEntity = FavoriteEntity.from(favorite);

        // when
        final Favorite convertFavorite = favoriteEntity.toModel();

        // then
        assertThat(convertFavorite.id()).isEqualTo(1);
        assertThat(convertFavorite.total()).isEqualTo(10);
        assertThat(convertFavorite.dayIncreaseCount()).isEqualTo(2);
        assertThat(convertFavorite.canIncrease()).isTrue();
        assertThat(convertFavorite.lastIncreaseAt()).isEqualTo(currentTime);
    }
}
