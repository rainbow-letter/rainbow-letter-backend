package com.handwoong.rainbowletter.temporary.domain.dto;

import lombok.Builder;

@Builder
public record TemporaryUpdate(Long id, Long petId, String content) {
}
