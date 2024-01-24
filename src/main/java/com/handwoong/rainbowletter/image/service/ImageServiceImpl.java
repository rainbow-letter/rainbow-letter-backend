package com.handwoong.rainbowletter.image.service;

import com.handwoong.rainbowletter.common.service.port.UuidGenerator;
import com.handwoong.rainbowletter.image.controller.port.ImageService;
import com.handwoong.rainbowletter.image.domain.Image;
import com.handwoong.rainbowletter.image.domain.ImageType;
import com.handwoong.rainbowletter.image.service.port.AmazonS3Service;
import com.handwoong.rainbowletter.image.service.port.ImageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ImageServiceImpl implements ImageService {
    private final UuidGenerator uuidGenerator;
    private final AmazonS3Service amazonS3Service;
    private final ImageRepository imageRepository;

    @Override
    @Transactional
    public Image upload(final MultipartFile file, final ImageType type) {
        final String objectKey = uuidGenerator.generate();
        final String imageUrl = amazonS3Service.upload(file, objectKey);
        final Image image = Image.create(type, objectKey, amazonS3Service.getBucketName(), imageUrl);
        return imageRepository.save(image);
    }
}
