package com.handwoong.rainbowletter.image.service.port;

import com.handwoong.rainbowletter.image.domain.Image;

public interface ImageRepository {
    void save(Image image);
}
