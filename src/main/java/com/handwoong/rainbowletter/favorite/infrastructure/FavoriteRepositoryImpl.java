package com.handwoong.rainbowletter.favorite.infrastructure;

import static com.handwoong.rainbowletter.favorite.infrastructure.QFavoriteEntity.favoriteEntity;

import com.handwoong.rainbowletter.favorite.domain.Favorite;
import com.handwoong.rainbowletter.favorite.service.port.FavoriteRepository;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class FavoriteRepositoryImpl implements FavoriteRepository {
    private final JPAQueryFactory queryFactory;
    private final FavoriteJpaRepository favoriteJpaRepository;

    @Override
    public Favorite save(final Favorite favorite) {
        return favoriteJpaRepository.save(FavoriteEntity.from(favorite)).toModel();
    }

    @Override
    public Optional<Favorite> findById(final Long id) {
        return favoriteJpaRepository.findById(id).map(FavoriteEntity::toModel);
    }

    @Override
    public void resetIncreaseCount() {
        queryFactory.update(favoriteEntity)
                .set(favoriteEntity.dayIncreaseCount, 0)
                .execute();
    }

    @Override
    public void resetCanIncrease() {
        queryFactory.update(favoriteEntity)
                .set(favoriteEntity.canIncrease, true)
                .execute();
    }
}
