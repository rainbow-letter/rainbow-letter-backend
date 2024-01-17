package com.handwoong.rainbowletter.pet.controller.response;

import com.handwoong.rainbowletter.image.controller.response.ImageResponse;
import java.time.LocalDate;
import lombok.Builder;

@Builder
public record DashboardResponse(
        Long id,
        String name,
        Long letterCount,
        int favoriteCount,
        ImageResponse image,
        LocalDate deathAnniversary
) {
}
