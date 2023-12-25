package com.handwoong.rainbowletter.domain.image.dto;

import com.handwoong.rainbowletter.domain.image.model.Image;

public record ImageUploadResponse(
        Long id
) {
    public static ImageUploadResponse from(final Image image) {
        return new ImageUploadResponse(image.getId());
    }
}
