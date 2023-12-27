package com.handwoong.rainbowletter.domain.favorite.controller;

import com.handwoong.rainbowletter.domain.favorite.dto.FavoriteResponse;
import com.handwoong.rainbowletter.domain.favorite.service.FavoriteService;
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
        final FavoriteResponse response = favoriteService.increase(id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
