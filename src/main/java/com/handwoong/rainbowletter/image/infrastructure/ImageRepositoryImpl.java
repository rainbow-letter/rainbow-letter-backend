package com.handwoong.rainbowletter.image.infrastructure;

import com.handwoong.rainbowletter.image.domain.Image;
import com.handwoong.rainbowletter.image.service.port.ImageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ImageRepositoryImpl implements ImageRepository {
    private final ImageJpaRepository imageJpaRepository;

    @Override
    public void save(final Image image) {
        imageJpaRepository.save(ImageEntity.fromModel(image));
    }
}
