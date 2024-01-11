package com.handwoong.rainbowletter.pet.controller.response;

import java.time.LocalDate;
import lombok.Builder;

@Builder
public record DashboardResponse(Long id, String name, Long letterCount, int favoriteCount, LocalDate deathAnniversary) {
}
