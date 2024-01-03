package com.handwoong.rainbowletter.favorite.service.port;

import com.handwoong.rainbowletter.favorite.domain.Favorite;
import java.util.Optional;

public interface FavoriteRepository {
    Favorite save(Favorite favorite);

    Optional<Favorite> findById(Long id);
}
