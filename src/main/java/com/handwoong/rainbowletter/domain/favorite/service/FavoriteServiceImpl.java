package com.handwoong.rainbowletter.domain.favorite.service;

import com.handwoong.rainbowletter.domain.favorite.dto.FavoriteResponse;
import com.handwoong.rainbowletter.domain.favorite.model.Favorite;
import com.handwoong.rainbowletter.domain.favorite.repository.FavoriteRepository;
import com.handwoong.rainbowletter.exception.ErrorCode;
import com.handwoong.rainbowletter.exception.RainbowLetterException;
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
