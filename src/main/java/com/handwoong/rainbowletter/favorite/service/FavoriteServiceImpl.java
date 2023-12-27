package com.handwoong.rainbowletter.favorite.service;

import com.handwoong.rainbowletter.common.exception.ErrorCode;
import com.handwoong.rainbowletter.common.exception.RainbowLetterException;
import com.handwoong.rainbowletter.favorite.infrastructure.Favorite;
import com.handwoong.rainbowletter.favorite.controller.response.FavoriteResponse;
import com.handwoong.rainbowletter.favorite.infrastructure.FavoriteRepository;
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
    public FavoriteResponse increase(final Long id) {
        final Favorite favorite = favoriteRepository.findById(id)
                .orElseThrow(() -> new RainbowLetterException(ErrorCode.RESOURCE_ID_NOT_FOUND, "Favorite"));
        favorite.increase();
        return FavoriteResponse.from(favorite);
    }
}
