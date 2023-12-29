package com.handwoong.rainbowletter.image.infrastructure;

import com.handwoong.rainbowletter.image.domain.Image;
import com.handwoong.rainbowletter.image.service.port.ImageRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ImageRepositoryImpl implements ImageRepository {
    private final ImageJpaRepository imageJpaRepository;

    @Override
    public Optional<Image> findById(final Long id) {
        return imageJpaRepository.findById(id).map(ImageEntity::toModel);
    }

    @Override
    public Image save(final Image image) {
        return imageJpaRepository.save(ImageEntity.from(image)).toModel();
    }
}
