package com.handwoong.rainbowletter.image.domain;

import lombok.Builder;

@Builder
public record Image(
        Long id,
        ImageType type,
        String objectKey,
        String bucket,
        String url
) {
    public static Image create(final ImageType type, final String objectKey, final String bucket, final String url) {
        return Image.builder()
                .type(type)
                .objectKey(objectKey)
                .bucket(bucket)
                .url(url)
                .build();
    }
}
