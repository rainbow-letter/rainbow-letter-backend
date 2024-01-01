package com.handwoong.rainbowletter.image.infrastructure;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.handwoong.rainbowletter.common.config.aws.S3Config;
import com.handwoong.rainbowletter.image.exception.ImageUploadFailException;
import com.handwoong.rainbowletter.image.service.port.AmazonS3Service;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
@RequiredArgsConstructor
public class AmazonS3ServiceImpl implements AmazonS3Service {
    private final S3Config s3Config;
    private final AmazonS3 amazonS3;

    @Override
    public String upload(final MultipartFile file, final String objectKey) {
        final ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentType(file.getContentType());
        objectMetadata.setContentLength(file.getSize());

        try {
            final PutObjectRequest request = new PutObjectRequest(
                    getBucketName(),
                    objectKey,
                    file.getInputStream(),
                    objectMetadata
            );
            amazonS3.putObject(request);
        } catch (IOException exception) {
            throw new ImageUploadFailException();
        }
        return amazonS3.getUrl(getBucketName(), objectKey).toString();
    }

    @Override
    public void remove(final String bucket, final String objectKey) {
        final DeleteObjectRequest request = new DeleteObjectRequest(bucket, objectKey);
        amazonS3.deleteObject(request);
    }

    @Override
    public String getBucketName() {
        return s3Config.getBucketName();
    }
}
