package com.handwoong.rainbowletter.favorite.controller;

import com.handwoong.rainbowletter.favorite.controller.port.FavoriteService;
import com.handwoong.rainbowletter.favorite.controller.response.FavoriteResponse;
import com.handwoong.rainbowletter.favorite.domain.Favorite;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/favorite")
public class FavoriteController {
    private final FavoriteService favoriteService;

    @PostMapping("/{id}")
    public ResponseEntity<FavoriteResponse> increase(@PathVariable final Long id) {
        final Favorite favorite = favoriteService.increase(id);
        final FavoriteResponse response = FavoriteResponse.from(favorite);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
