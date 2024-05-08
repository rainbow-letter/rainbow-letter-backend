package com.handwoong.rainbowletter.temporary.controller.request;

import com.handwoong.rainbowletter.temporary.domain.dto.TemporaryCreate;

public record TemporaryCreateRequest(Long petId, String content) {

    public TemporaryCreate toDto() {
        return TemporaryCreate.builder()
                .petId(petId)
                .content(content)
                .build();
    }
}
