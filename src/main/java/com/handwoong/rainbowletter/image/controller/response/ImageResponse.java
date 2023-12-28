package com.handwoong.rainbowletter.image.controller.response;

import com.handwoong.rainbowletter.image.domain.Image;
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
        return new ImageResponse(image.id(), image.objectKey(), image.url());
    }
}
