package com.handwoong.rainbowletter.temporary.controller.request;

import com.handwoong.rainbowletter.temporary.domain.dto.TemporaryUpdate;

public record TemporaryUpdateRequest(Long id, Long petId, String content) {

    public TemporaryUpdate toDto() {
        return TemporaryUpdate.builder()
                .id(id)
                .petId(petId)
                .content(content)
                .build();
    }
}
