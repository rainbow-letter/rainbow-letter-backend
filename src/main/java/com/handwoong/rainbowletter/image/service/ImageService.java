package com.handwoong.rainbowletter.image.service;

import com.handwoong.rainbowletter.image.domain.Image;
import org.springframework.web.multipart.MultipartFile;

public interface ImageService {
    Image upload(final MultipartFile file, final String type);

    void remove(final Image image);
}
