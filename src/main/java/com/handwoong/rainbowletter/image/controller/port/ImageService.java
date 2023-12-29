package com.handwoong.rainbowletter.image.controller.port;

import com.handwoong.rainbowletter.image.domain.Image;
import com.handwoong.rainbowletter.image.domain.ImageType;
import jakarta.annotation.Nullable;
import org.springframework.web.multipart.MultipartFile;

public interface ImageService {
    Image findByIdOrElseThrow(Long id);

    Image findById(@Nullable Long id);

    Image upload(MultipartFile file, ImageType type);

    void remove(Image image);
}
