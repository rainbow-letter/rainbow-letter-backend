package com.handwoong.rainbowletter.mock.image;

import com.handwoong.rainbowletter.image.domain.Image;
import com.handwoong.rainbowletter.image.exception.ImageResourceNotFoundException;
import com.handwoong.rainbowletter.image.service.port.ImageRepository;
import jakarta.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

public class FakeImageRepository implements ImageRepository {
    private final Map<Long, Image> database = new HashMap<>();

    private Long sequence = 1L;

    @Override
    public Image findByIdOrElseThrow(final Long id) {
        return findById(id)
                .orElseThrow(() -> new ImageResourceNotFoundException(id));
    }

    @Override
    public Optional<Image> findById(final Long id) {
        return database.values()
                .stream()
                .filter(image -> image.id().equals(id))
                .findAny();
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
        Long id = Objects.nonNull(image.id()) ? image.id() : sequence++;
        final Image saveImage = createImage(id, image);
        database.put(id, saveImage);
        return saveImage;
    }

    private Image createImage(final Long id, final Image image) {
        return Image.builder()
                .id(id)
                .type(image.type())
                .bucket(image.bucket())
                .url(image.url())
                .objectKey(image.objectKey())
                .build();
    }

    @Override
    public void delete(final Image image) {
        database.remove(image.id());
    }
}
