package com.handwoong.rainbowletter.favorite.service;

import com.handwoong.rainbowletter.favorite.controller.response.FavoriteResponse;

public interface FavoriteService {
    FavoriteResponse increase(final Long id);
}
