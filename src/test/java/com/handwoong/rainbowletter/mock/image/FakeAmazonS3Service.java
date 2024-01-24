package com.handwoong.rainbowletter.mock.image;

import com.handwoong.rainbowletter.image.service.port.AmazonS3Service;
import org.springframework.web.multipart.MultipartFile;

public class FakeAmazonS3Service implements AmazonS3Service {
    public MultipartFile file;
    public String bucket;
    public String objectKey;

    @Override
    public String upload(final MultipartFile file, final String objectKey) {
        this.file = file;
        this.objectKey = objectKey;
        return "http://rainbowletter/image/" + file.getName() + "/" + objectKey;
    }

    @Override
    public void remove(final String bucket, final String objectKey) {
        this.bucket = bucket;
        this.objectKey = objectKey;
    }

    @Override
    public String getBucketName() {
        return "rainbowletter";
    }
}
