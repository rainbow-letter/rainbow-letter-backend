package com.handwoong.rainbowletter.image.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class ImageTest {
    @Test
    void 이미지를_생성한다() {
        // given
        // when
        final Image image = Image.builder()
                .id(1L)
                .type(ImageType.PET)
                .bucket("rainbowletter")
                .objectKey("aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa")
                .url("http://rainbowletter/image")
                .build();

        // then
        assertThat(image.id()).isEqualTo(1);
        assertThat(image.type()).isEqualTo(ImageType.PET);
        assertThat(image.bucket()).isEqualTo("rainbowletter");
        assertThat(image.objectKey()).isEqualTo("aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa");
        assertThat(image.url()).isEqualTo("http://rainbowletter/image");
    }
}