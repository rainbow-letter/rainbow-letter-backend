package com.handwoong.rainbowletter.domain.image.service;

import com.handwoong.rainbowletter.domain.image.dto.ImageUploadResponse;
import com.handwoong.rainbowletter.domain.image.model.Image;
import org.springframework.web.multipart.MultipartFile;

public interface ImageService {
    ImageUploadResponse upload(final MultipartFile file, final String type);

    void remove(final Image image);
}
