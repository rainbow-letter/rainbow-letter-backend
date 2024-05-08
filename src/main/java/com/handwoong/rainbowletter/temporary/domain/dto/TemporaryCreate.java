package com.handwoong.rainbowletter.temporary.domain.dto;

import lombok.Builder;

@Builder
public record TemporaryCreate(Long petId, String content) {
}
