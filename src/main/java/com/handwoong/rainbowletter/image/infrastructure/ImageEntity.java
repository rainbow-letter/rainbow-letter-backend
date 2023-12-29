package com.handwoong.rainbowletter.image.infrastructure;

import com.handwoong.rainbowletter.common.infrastructure.BaseEntity;
import com.handwoong.rainbowletter.image.domain.Image;
import com.handwoong.rainbowletter.image.domain.ImageType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
@Entity
@Table(name = "image")
public class ImageEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Enumerated(EnumType.STRING)
    private ImageType type;

    @NotNull
    private String objectKey;

    @NotNull
    private String bucket;

    @NotNull
    private String url;

    public static ImageEntity from(final Image image) {
        final ImageEntity imageEntity = new ImageEntity();
        imageEntity.id = image.id();
        imageEntity.type = image.type();
        imageEntity.objectKey = image.objectKey();
        imageEntity.bucket = image.bucket();
        imageEntity.url = image.url();
        return imageEntity;
    }

    public Image toModel() {
        return Image.builder()
                .id(id)
                .type(type)
                .objectKey(objectKey)
                .bucket(bucket)
                .url(url)
                .build();
    }
}
