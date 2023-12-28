package com.handwoong.rainbowletter.image.domain;

import lombok.Builder;

@Builder
public record Image(
        Long id,
        String type,
        String objectKey,
        String bucket,
        String url
) {
    public static Image create(final String type, final String objectKey, final String bucket, final String url) {
        return Image.builder()
                .type(type)
                .objectKey(objectKey)
                .bucket(bucket)
                .url(url)
                .build();
    }
}
