package com.handwoong.rainbowletter.image.controller.response;

import com.handwoong.rainbowletter.image.domain.Image;
import lombok.Builder;

@Builder
public record ImageUploadResponse(Long id) {
    public static ImageUploadResponse from(final Image image) {
        return ImageUploadResponse.builder()
                .id(image.id())
                .build();
    }
}
