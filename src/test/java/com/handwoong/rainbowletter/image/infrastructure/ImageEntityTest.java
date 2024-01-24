package com.handwoong.rainbowletter.image.infrastructure;

import static org.assertj.core.api.Assertions.assertThat;

import com.handwoong.rainbowletter.image.domain.Image;
import com.handwoong.rainbowletter.image.domain.ImageType;
import org.junit.jupiter.api.Test;

class ImageEntityTest {
    @Test
    void 이미지_도메인으로_엔티티를_생성한다() {
        // given
        final Image image = Image.builder()
                .id(1L)
                .type(ImageType.PET)
                .bucket("rainbowletter")
                .objectKey("aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa")
                .url("http://rainbowletter/image")
                .build();

        // when
        final ImageEntity imageEntity = ImageEntity.from(image);

        // then
        assertThat(imageEntity.getId()).isEqualTo(1);
        assertThat(imageEntity.getType()).isEqualTo(ImageType.PET);
        assertThat(imageEntity.getBucket()).isEqualTo("rainbowletter");
        assertThat(imageEntity.getObjectKey()).isEqualTo("aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa");
        assertThat(imageEntity.getUrl()).isEqualTo("http://rainbowletter/image");
    }

    @Test
    void 엔티티로_이미지_도메인을_생성한다() {
        // given
        final Image image = Image.builder()
                .id(1L)
                .type(ImageType.PET)
                .bucket("rainbowletter")
                .objectKey("aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa")
                .url("http://rainbowletter/image")
                .build();

        final ImageEntity imageEntity = ImageEntity.from(image);

        // when
        final Image convertImage = imageEntity.toModel();

        // then
        assertThat(image.id()).isEqualTo(1);
        assertThat(image.type()).isEqualTo(ImageType.PET);
        assertThat(image.bucket()).isEqualTo("rainbowletter");
        assertThat(image.objectKey()).isEqualTo("aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa");
        assertThat(image.url()).isEqualTo("http://rainbowletter/image");
    }
}
