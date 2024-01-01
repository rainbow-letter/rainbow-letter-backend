package com.handwoong.rainbowletter.favorite.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.handwoong.rainbowletter.favorite.domain.Favorite;
import com.handwoong.rainbowletter.favorite.exception.FavoriteIncreaseNotValidException;
import com.handwoong.rainbowletter.mock.favorite.FakeFavoriteRepository;
import com.handwoong.rainbowletter.mock.favorite.FavoriteTestContainer;
import java.time.LocalDate;
import java.time.LocalDateTime;
import org.junit.jupiter.api.Test;

class FavoriteServiceTest {
    @Test
    void 좋아요를_생성한다() {
        // given
        final FavoriteTestContainer testContainer = new FavoriteTestContainer();

        // when
        final Favorite favorite = testContainer.service.create();

        // then
        assertThat(favorite.id()).isEqualTo(1);
        assertThat(favorite.total()).isZero();
        assertThat(favorite.dayIncreaseCount()).isZero();
        assertThat(favorite.canIncrease()).isTrue();
        assertThat(favorite.lastIncreaseAt().toLocalDate()).isEqualTo(LocalDateTime.now().toLocalDate());
    }

    @Test
    void 좋아요를_증가시킨다() {
        // given
        final FavoriteTestContainer testContainer = new FavoriteTestContainer();
        testContainer.service.create();

        // when
        final Favorite increasedFavorite = testContainer.service.increase(1L);

        // then
        assertThat(increasedFavorite.id()).isEqualTo(1);
        assertThat(increasedFavorite.total()).isEqualTo(1);
        assertThat(increasedFavorite.dayIncreaseCount()).isEqualTo(1);
        assertThat(increasedFavorite.canIncrease()).isTrue();
        assertThat(increasedFavorite.lastIncreaseAt().toLocalDate()).isEqualTo(LocalDateTime.now().toLocalDate());
    }

    @Test
    void 좋아요_증가전_업데이트_날짜가_과거라면_일일_좋아요_가능_횟수를_초기화한다() {
        // given
        final Favorite favorite = Favorite.builder()
                .total(10)
                .dayIncreaseCount(3)
                .canIncrease(false)
                .build();

        final FakeFavoriteRepository favoriteRepository =
                new FakeFavoriteRepository(LocalDate.of(1900, 1, 1).atStartOfDay());
        favoriteRepository.save(favorite);

        final FavoriteServiceImpl favoriteService = new FavoriteServiceImpl(favoriteRepository);

        // when
        final Favorite increasedFavorite = favoriteService.increase(1L);

        // then
        assertThat(increasedFavorite.id()).isEqualTo(1);
        assertThat(increasedFavorite.total()).isEqualTo(11);
        assertThat(increasedFavorite.dayIncreaseCount()).isEqualTo(1);
        assertThat(increasedFavorite.canIncrease()).isTrue();
        assertThat(increasedFavorite.lastIncreaseAt().toLocalDate()).isEqualTo(LocalDateTime.now().toLocalDate());
    }

    @Test
    void 좋아요_증가시_일일_좋아요_횟수가_소진되어_있다면_예외가_발생한다() {
        // given
        final Favorite favorite = Favorite.builder()
                .total(10)
                .dayIncreaseCount(3)
                .canIncrease(false)
                .build();

        final FakeFavoriteRepository favoriteRepository = new FakeFavoriteRepository();
        favoriteRepository.save(favorite);

        final FavoriteServiceImpl favoriteService = new FavoriteServiceImpl(favoriteRepository);

        // when
        // then
        assertThatThrownBy(() -> favoriteService.increase(1L))
                .isInstanceOf(FavoriteIncreaseNotValidException.class)
                .hasMessage("하루 최대 좋아요 수를 달성하였습니다.");
    }
}
