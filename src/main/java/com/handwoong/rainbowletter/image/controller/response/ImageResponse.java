package com.handwoong.rainbowletter.image.controller.response;

import com.handwoong.rainbowletter.image.domain.Image;
import java.util.Objects;
import lombok.Builder;

@Builder
public record ImageResponse(Long id, String objectKey, String url) {
    public static ImageResponse from(final Image image) {
        if (Objects.isNull(image)) {
            return ImageResponse.builder()
                    .id(null)
                    .objectKey(null)
                    .url(null)
                    .build();
        }
        return ImageResponse.builder()
                .id(image.id())
                .objectKey(image.objectKey())
                .url(image.url())
                .build();
    }
}
