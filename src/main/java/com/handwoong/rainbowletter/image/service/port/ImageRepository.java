package com.handwoong.rainbowletter.image.service.port;

import com.handwoong.rainbowletter.image.domain.Image;
import java.util.Optional;

public interface ImageRepository {
    Optional<Image> findById(Long id);

    void save(Image image);
}
