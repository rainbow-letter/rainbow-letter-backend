package com.handwoong.rainbowletter.image.service.port;

import com.handwoong.rainbowletter.image.domain.Image;
import jakarta.annotation.Nullable;
import java.util.Optional;

public interface ImageRepository {
    Image findByIdOrElseThrow(Long id);

    Optional<Image> findById(Long id);

    Image findByNullableId(@Nullable Long id);

    Image save(Image image);

    void delete(Image image);
}
