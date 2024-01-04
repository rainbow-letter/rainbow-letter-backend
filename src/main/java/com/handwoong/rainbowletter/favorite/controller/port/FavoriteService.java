package com.handwoong.rainbowletter.favorite.controller.port;

import com.handwoong.rainbowletter.favorite.domain.Favorite;

public interface FavoriteService {
    Favorite increase(Long id);
}
