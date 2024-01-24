package com.handwoong.rainbowletter.favorite.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.handwoong.rainbowletter.favorite.exception.FavoriteIncreaseNotValidException;
import java.time.LocalDateTime;
import org.junit.jupiter.api.Test;

class FavoriteTest {
    @Test
    void 좋아요를_생성한다() {
        // given
        // when
        final Favorite favorite = Favorite.create();

        // then
        assertThat(favorite.id()).isNull();
        assertThat(favorite.total()).isZero();
        assertThat(favorite.dayIncreaseCount()).isZero();
        assertThat(favorite.canIncrease()).isTrue();
        assertThat(favorite.lastIncreaseAt().toLocalDate()).isEqualTo(LocalDateTime.now().toLocalDate());
    }

    @Test
    void 좋아요를_증가시킨다() {
        // given
        final Favorite favorite = Favorite.builder()
                .id(1L)
                .total(10)
                .dayIncreaseCount(0)
                .canIncrease(true)
                .lastIncreaseAt(LocalDateTime.of(1900, 1, 1, 0, 0))
                .build();

        // when
        final Favorite increaseFavorite = favorite.increase();

        // then
        assertThat(increaseFavorite.id()).isEqualTo(1);
        assertThat(increaseFavorite.total()).isEqualTo(11);
        assertThat(increaseFavorite.dayIncreaseCount()).isEqualTo(1);
        assertThat(increaseFavorite.canIncrease()).isTrue();
        assertThat(increaseFavorite.lastIncreaseAt().toLocalDate()).isEqualTo(LocalDateTime.now().toLocalDate());
    }

    @Test
    void 일일_좋아요_횟수가_소진되면_예외가_발생한다() {
        // given
        final Favorite favorite = Favorite.builder()
                .id(1L)
                .total(10)
                .dayIncreaseCount(3)
                .canIncrease(false)
                .build();

        // when
        // then
        assertThatThrownBy(favorite::increase)
                .isInstanceOf(FavoriteIncreaseNotValidException.class)
                .hasMessage("하루 최대 좋아요 수를 달성하였습니다.");
    }
}