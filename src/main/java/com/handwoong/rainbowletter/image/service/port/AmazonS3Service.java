package com.handwoong.rainbowletter.image.service.port;

import org.springframework.web.multipart.MultipartFile;

public interface AmazonS3Service {
    String upload(MultipartFile file, String objectKey);

    void remove(String bucket, String objectKey);

    String getBucketName();
}
