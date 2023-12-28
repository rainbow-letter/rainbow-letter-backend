package com.handwoong.rainbowletter.favorite.service;

import com.handwoong.rainbowletter.favorite.domain.Favorite;
import com.handwoong.rainbowletter.favorite.exception.FavoriteResourceNotFoundException;
import com.handwoong.rainbowletter.favorite.service.port.FavoriteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class FavoriteServiceImpl implements FavoriteService {
    private final FavoriteRepository favoriteRepository;

    @Override
    @Transactional
    public Favorite increase(final Long id) {
        final Favorite favorite = favoriteRepository.findById(id)
                .orElseThrow(() -> new FavoriteResourceNotFoundException(id));
        final Favorite checkedFavorite = favorite.checkRequireInitDayCount();
        final Favorite updateFavorite = checkedFavorite.increase();
        favoriteRepository.save(updateFavorite);
        return updateFavorite;
    }
}
