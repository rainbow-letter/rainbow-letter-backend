package com.handwoong.rainbowletter.image.controller.port;

import com.handwoong.rainbowletter.image.domain.Image;
import com.handwoong.rainbowletter.image.domain.ImageType;
import org.springframework.web.multipart.MultipartFile;

public interface ImageService {
    Image upload(MultipartFile file, ImageType type);
}
