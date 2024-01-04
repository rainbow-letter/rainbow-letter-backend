package com.handwoong.rainbowletter.image.infrastructure;

import com.handwoong.rainbowletter.image.domain.Image;
import com.handwoong.rainbowletter.image.exception.ImageResourceNotFoundException;
import com.handwoong.rainbowletter.image.service.port.ImageRepository;
import jakarta.annotation.Nullable;
import java.util.Objects;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ImageRepositoryImpl implements ImageRepository {
    private final ImageJpaRepository imageJpaRepository;

    @Override
    public Image findByIdOrElseThrow(final Long id) {
        return findById(id)
                .orElseThrow(() -> new ImageResourceNotFoundException(id));
    }

    @Override
    public Optional<Image> findById(final Long id) {
        return imageJpaRepository.findById(id).map(ImageEntity::toModel);
    }

    @Override
    public Image findByNullableId(@Nullable final Long id) {
        if (Objects.isNull(id)) {
            return null;
        }
        return findByIdOrElseThrow(id);
    }

    @Override
    public Image save(final Image image) {
        return imageJpaRepository.save(ImageEntity.from(image)).toModel();
    }

    @Override
    public void delete(final Image image) {
        imageJpaRepository.delete(ImageEntity.from(image));
    }
}
