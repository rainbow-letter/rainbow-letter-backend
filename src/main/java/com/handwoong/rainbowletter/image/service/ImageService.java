package com.handwoong.rainbowletter.image.service;

import com.handwoong.rainbowletter.image.domain.Image;
import jakarta.annotation.Nullable;
import org.springframework.web.multipart.MultipartFile;

public interface ImageService {
    Image findByIdOrElseThrow(Long id);

    Image findById(@Nullable Long id);

    Image upload(MultipartFile file, String type);

    void remove(Image image);
}
