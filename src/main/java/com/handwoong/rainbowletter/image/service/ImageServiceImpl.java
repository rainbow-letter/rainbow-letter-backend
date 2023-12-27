package com.handwoong.rainbowletter.image.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.handwoong.rainbowletter.common.config.aws.S3Config;
import com.handwoong.rainbowletter.common.exception.ErrorCode;
import com.handwoong.rainbowletter.common.exception.RainbowLetterException;
import com.handwoong.rainbowletter.image.controller.response.ImageUploadResponse;
import com.handwoong.rainbowletter.image.infrastructure.Image;
import com.handwoong.rainbowletter.image.infrastructure.ImageRepository;
import java.io.IOException;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ImageServiceImpl implements ImageService {
    private final AmazonS3 amazonS3;
    private final ImageRepository imageRepository;
    private final S3Config s3Config;

    @Override
    @Transactional
    public ImageUploadResponse upload(final MultipartFile file, final String type) {
        final String objectKey = UUID.randomUUID().toString();
        final String imageUrl = requestAmazonS3Upload(file, objectKey);
        final Image image = new Image(type, objectKey, s3Config.getBucketName(), imageUrl);
        imageRepository.save(image);
        return ImageUploadResponse.from(image);
    }

    @Override
    @Transactional
    public void remove(final Image image) {
        final DeleteObjectRequest deleteObjectRequest = new DeleteObjectRequest(
                s3Config.getBucketName(),
                image.getObjectKey()
        );
        amazonS3.deleteObject(deleteObjectRequest);
    }

    private String requestAmazonS3Upload(final MultipartFile file, final String objectKey) {
        final ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentType(file.getContentType());
        objectMetadata.setContentLength(file.getSize());

        final String bucketName = s3Config.getBucketName();

        try {
            final PutObjectRequest putObjectRequest = new PutObjectRequest(
                    bucketName,
                    objectKey,
                    file.getInputStream(),
                    objectMetadata
            );
            amazonS3.putObject(putObjectRequest);
        } catch (IOException exception) {
            throw new RainbowLetterException(ErrorCode.FAIL_UPLOAD_IMAGE);
        }
        return amazonS3.getUrl(bucketName, objectKey).toString();
    }
}
