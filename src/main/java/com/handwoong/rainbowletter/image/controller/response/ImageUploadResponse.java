package com.handwoong.rainbowletter.image.controller.response;

import com.handwoong.rainbowletter.image.domain.Image;

public record ImageUploadResponse(Long id) {
    public static ImageUploadResponse from(final Image image) {
        return new ImageUploadResponse(image.id());
    }
}
