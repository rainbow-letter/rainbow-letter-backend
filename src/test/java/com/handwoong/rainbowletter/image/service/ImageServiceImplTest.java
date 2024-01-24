package com.handwoong.rainbowletter.image.service;

import static com.handwoong.rainbowletter.mock.image.ImageTestContainer.mockFile;
import static org.assertj.core.api.Assertions.assertThat;

import com.handwoong.rainbowletter.image.domain.Image;
import com.handwoong.rainbowletter.image.domain.ImageType;
import com.handwoong.rainbowletter.mock.image.ImageTestContainer;
import java.io.IOException;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockMultipartFile;

class ImageServiceImplTest {
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
}
