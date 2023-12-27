package com.handwoong.rainbowletter.domain.favorite.service;

import com.handwoong.rainbowletter.domain.favorite.dto.FavoriteResponse;

public interface FavoriteService {
    FavoriteResponse increase(final Long id);
}
