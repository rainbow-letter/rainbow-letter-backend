package com.handwoong.rainbowletter.domain.image.dto;

import com.handwoong.rainbowletter.domain.image.model.Image;
import java.util.Objects;

public record ImageResponse(
        Long id,
        String objectKey,
        String url
) {
    public static ImageResponse from(final Image image) {
        if (Objects.isNull(image)) {
            return null;
        }
        return new ImageResponse(image.getId(), image.getObjectKey(), image.getUrl());
    }
}
