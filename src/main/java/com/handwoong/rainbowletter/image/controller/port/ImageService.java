package com.handwoong.rainbowletter.image.controller.port;

import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

import com.handwoong.rainbowletter.image.controller.response.ImageLoadResponse;
import com.handwoong.rainbowletter.image.domain.Image;
import com.handwoong.rainbowletter.image.domain.ImageType;

public interface ImageService {
    ImageLoadResponse load(final String dirName, final String fileName) throws IOException;

    Image upload(MultipartFile file, ImageType type);
}
