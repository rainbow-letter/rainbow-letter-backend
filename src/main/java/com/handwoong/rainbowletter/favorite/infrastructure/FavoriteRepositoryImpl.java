package com.handwoong.rainbowletter.favorite.infrastructure;

import com.handwoong.rainbowletter.favorite.domain.Favorite;
import com.handwoong.rainbowletter.favorite.service.port.FavoriteRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class FavoriteRepositoryImpl implements FavoriteRepository {
    private final FavoriteJpaRepository favoriteJpaRepository;

    @Override
    public void save(final Favorite favorite) {
        favoriteJpaRepository.save(FavoriteEntity.fromModel(favorite));
    }

    @Override
    public Optional<Favorite> findById(final Long id) {
        return favoriteJpaRepository.findById(id).map(FavoriteEntity::toModel);
    }
}
