package com.handwoong.rainbowletter.pet.controller.response;

import java.util.List;

public record PetListResponse(
        List<PetResponse> petResponses
) {
}
