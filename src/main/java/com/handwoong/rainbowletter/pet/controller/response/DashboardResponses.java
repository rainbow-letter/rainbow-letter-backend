package com.handwoong.rainbowletter.pet.controller.response;

import java.util.List;
import lombok.Builder;

@Builder
public record DashboardResponses(List<DashboardResponse> pets) {
    public static DashboardResponses from(final List<DashboardResponse> pets) {
        return DashboardResponses.builder()
                .pets(pets)
                .build();
    }
}
