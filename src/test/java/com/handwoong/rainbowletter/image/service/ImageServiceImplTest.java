package com.handwoong.rainbowletter.image.service;

import static com.handwoong.rainbowletter.mock.image.ImageTestContainer.mockFile;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.handwoong.rainbowletter.image.domain.Image;
import com.handwoong.rainbowletter.image.domain.ImageType;
import com.handwoong.rainbowletter.image.exception.ImageResourceNotFoundException;
import com.handwoong.rainbowletter.mock.image.FakeAmazonS3Service;
import com.handwoong.rainbowletter.mock.image.ImageTestContainer;
import java.io.IOException;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockMultipartFile;

class ImageServiceImplTest {
    @Test
    void NOTNULL_ID로_이미지를_찾는다() {
        // given
        final Image image = Image.builder()
                .type(ImageType.PET)
                .bucket("rainbowletter")
                .objectKey("aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa")
                .url("http://rainbowletter/image")
                .build();

        final ImageTestContainer testContainer = new ImageTestContainer();
        testContainer.repository.save(image);

        // when
        final Image findImage = testContainer.service.findByIdOrElseThrow(1L);

        // then
        assertThat(findImage.id()).isEqualTo(1);
        assertThat(findImage.type()).isEqualTo(ImageType.PET);
        assertThat(findImage.bucket()).isEqualTo("rainbowletter");
        assertThat(findImage.objectKey()).isEqualTo("aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa");
        assertThat(findImage.url()).isEqualTo("http://rainbowletter/image");
    }

    @Test
    void NOTNULL_ID로_이미지를_찾지_못하면_예외가_발생한다() {
        // given
        final ImageTestContainer testContainer = new ImageTestContainer();

        // when
        // then
        assertThatThrownBy(() -> testContainer.service.findByIdOrElseThrow(1L))
                .isInstanceOf(ImageResourceNotFoundException.class)
                .hasMessage("해당 이미지를 찾을 수 없습니다.");
    }

    @Test
    void NULLABLE_ID로_이미지를_찾는다() {
        // given
        final Image image = Image.builder()
                .type(ImageType.PET)
                .bucket("rainbowletter")
                .objectKey("aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa")
                .url("http://rainbowletter/image")
                .build();

        final ImageTestContainer testContainer = new ImageTestContainer();
        testContainer.repository.save(image);

        // when
        final Image findImage = testContainer.service.findById(1L);

        // then
        assertThat(findImage.id()).isEqualTo(1);
        assertThat(findImage.type()).isEqualTo(ImageType.PET);
        assertThat(findImage.bucket()).isEqualTo("rainbowletter");
        assertThat(findImage.objectKey()).isEqualTo("aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa");
        assertThat(findImage.url()).isEqualTo("http://rainbowletter/image");
    }

    @Test
    void NULLABLE_ID로_이미지를_찾지_못하면_예외가_발생한다() {
        // given
        final ImageTestContainer testContainer = new ImageTestContainer();

        // when
        // then
        assertThatThrownBy(() -> testContainer.service.findById(1L))
                .isInstanceOf(ImageResourceNotFoundException.class)
                .hasMessage("해당 이미지를 찾을 수 없습니다.");
    }

    @Test
    void NULLABLE_ID가_NULL인_경우_NULL을_반환한다() {
        // given
        final ImageTestContainer testContainer = new ImageTestContainer();

        // when
        final Image image = testContainer.service.findById(null);

        // then
        assertThat(image).isNull();
    }

    @Test
    void 이미지를_업로드한다() throws IOException {
        // given
        final MockMultipartFile file = mockFile();

        final ImageTestContainer testContainer = new ImageTestContainer();

        // when
        final Image image = testContainer.service.upload(file, ImageType.PET);

        // then
        assertThat(image.id()).isEqualTo(1);
        assertThat(image.type()).isEqualTo(ImageType.PET);
        assertThat(image.bucket()).isEqualTo("rainbowletter");
        assertThat(image.objectKey()).isEqualTo("aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa");
        assertThat(image.url()).isEqualTo(
                "http://rainbowletter/image/rainbow-letter-logo/aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa");
    }

    @Test
    void 이미지를_삭제한다() {
        // given
        final Image image = Image.builder()
                .type(ImageType.PET)
                .bucket("rainbowletter")
                .objectKey("aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa")
                .url("http://rainbowletter/image")
                .build();

        final ImageTestContainer testContainer = new ImageTestContainer();

        // when
        testContainer.service.remove(image);

        // then
        final FakeAmazonS3Service result = testContainer.amazonS3Service;
        assertThat(result.bucket).isEqualTo("rainbowletter");
        assertThat(result.objectKey).isEqualTo("aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa");
    }
}
