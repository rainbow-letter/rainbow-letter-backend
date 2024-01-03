package com.handwoong.rainbowletter.mock.image;

import com.handwoong.rainbowletter.image.controller.port.ImageService;
import com.handwoong.rainbowletter.image.service.ImageServiceImpl;
import com.handwoong.rainbowletter.image.service.port.ImageRepository;
import com.handwoong.rainbowletter.mock.common.FakeUuidGenerator;
import java.io.FileInputStream;
import java.io.IOException;
import org.springframework.mock.web.MockMultipartFile;

public class ImageTestContainer {
    public final ImageRepository repository;
    public final FakeAmazonS3Service amazonS3Service;
    public final ImageService service;

    public ImageTestContainer() {
        this.repository = new FakeImageRepository();
        this.amazonS3Service = new FakeAmazonS3Service();
        this.service = new ImageServiceImpl(new FakeUuidGenerator(), amazonS3Service, repository);
    }

    public static MockMultipartFile mockFile() throws IOException {
        final String fileName = "rainbow-letter-logo";
        final String contentType = "png";
        final String path = "src/test/resources/image/rainbow-letter-logo.png";

        final FileInputStream imageInputStream = new FileInputStream(path);
        return new MockMultipartFile(fileName, fileName + "." + contentType, contentType, imageInputStream);
    }
}
