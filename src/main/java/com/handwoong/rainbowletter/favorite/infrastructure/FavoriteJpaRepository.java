package com.handwoong.rainbowletter.favorite.infrastructure;

import org.springframework.data.jpa.repository.JpaRepository;

public interface FavoriteJpaRepository extends JpaRepository<FavoriteEntity, Long> {
}
