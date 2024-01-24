package com.handwoong.rainbowletter.mock.favorite;

import com.handwoong.rainbowletter.favorite.controller.port.FavoriteService;
import com.handwoong.rainbowletter.favorite.service.FavoriteServiceImpl;
import com.handwoong.rainbowletter.favorite.service.port.FavoriteRepository;

public class FavoriteTestContainer {
    public final FavoriteRepository repository;
    public final FavoriteService service;

    public FavoriteTestContainer() {
        this.repository = new FakeFavoriteRepository();
        this.service = new FavoriteServiceImpl(repository);
    }
}
