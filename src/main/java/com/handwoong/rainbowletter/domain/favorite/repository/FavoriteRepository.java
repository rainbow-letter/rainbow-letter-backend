package com.handwoong.rainbowletter.domain.favorite.repository;

import com.handwoong.rainbowletter.domain.favorite.model.Favorite;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FavoriteRepository extends JpaRepository<Favorite, Long> {
}
