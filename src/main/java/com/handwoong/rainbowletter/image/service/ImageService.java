package com.handwoong.rainbowletter.image.service;

import com.handwoong.rainbowletter.image.controller.response.ImageUploadResponse;
import com.handwoong.rainbowletter.image.infrastructure.Image;
import org.springframework.web.multipart.MultipartFile;

public interface ImageService {
    ImageUploadResponse upload(final MultipartFile file, final String type);

    void remove(final Image image);
}
