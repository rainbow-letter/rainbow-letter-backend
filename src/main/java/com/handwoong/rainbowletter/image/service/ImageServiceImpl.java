package com.handwoong.rainbowletter.image.service;

import com.handwoong.rainbowletter.common.service.port.UuidGenerator;
import com.handwoong.rainbowletter.image.controller.port.ImageService;
import com.handwoong.rainbowletter.image.domain.Image;
import com.handwoong.rainbowletter.image.domain.ImageType;
import com.handwoong.rainbowletter.image.exception.ImageResourceNotFoundException;
import com.handwoong.rainbowletter.image.service.port.AmazonS3Service;
import com.handwoong.rainbowletter.image.service.port.ImageRepository;
import jakarta.annotation.Nullable;
import java.util.Objects;
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
    public Image findByIdOrElseThrow(final Long id) {
        return imageRepository.findById(id)
                .orElseThrow(() -> new ImageResourceNotFoundException(id));
    }

    @Override
    public Image findById(@Nullable final Long id) {
        if (Objects.isNull(id)) {
            return null;
        }
        return findByIdOrElseThrow(id);
    }

    @Override
    @Transactional
    public Image upload(final MultipartFile file, final ImageType type) {
        final String objectKey = uuidGenerator.generate();
        final String imageUrl = amazonS3Service.upload(file, objectKey);
        final Image image = Image.create(type, objectKey, amazonS3Service.getBucketName(), imageUrl);
        return imageRepository.save(image);
    }

    @Override
    @Transactional
    public void remove(final Image image) {
        amazonS3Service.remove(image.bucket(), image.objectKey());
    }
}
