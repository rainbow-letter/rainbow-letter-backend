package com.handwoong.rainbowletter.image.service;

import com.handwoong.rainbowletter.common.config.aws.S3Config;
import com.handwoong.rainbowletter.image.domain.Image;
import com.handwoong.rainbowletter.image.service.port.AmazonS3Service;
import com.handwoong.rainbowletter.image.service.port.ImageRepository;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ImageServiceImpl implements ImageService {
    private final S3Config s3Config;
    private final AmazonS3Service amazonS3Service;
    private final ImageRepository imageRepository;

    @Override
    @Transactional
    public Image upload(final MultipartFile file, final String type) {
        final String objectKey = UUID.randomUUID().toString();
        final String imageUrl = amazonS3Service.upload(file, s3Config.getBucketName(), objectKey);
        final Image image = Image.create(type, objectKey, s3Config.getBucketName(), imageUrl);
        imageRepository.save(image);
        return image;
    }

    @Override
    @Transactional
    public void remove(final Image image) {
        amazonS3Service.remove(image.bucket(), image.objectKey());
    }
}
