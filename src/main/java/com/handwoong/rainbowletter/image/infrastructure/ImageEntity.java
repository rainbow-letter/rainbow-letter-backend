package com.handwoong.rainbowletter.image.infrastructure;

import com.handwoong.rainbowletter.common.infrastructure.BaseEntity;
import com.handwoong.rainbowletter.image.domain.Image;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "image")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ImageEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String type;

    @NotNull
    private String objectKey;

    @NotNull
    private String bucket;

    @NotNull
    private String url;

    public Image toModel() {
        return Image.builder()
                .id(id)
                .type(type)
                .objectKey(objectKey)
                .bucket(bucket)
                .url(url)
                .build();
    }

    public static ImageEntity fromModel(final Image image) {
        final ImageEntity imageEntity = new ImageEntity();
        imageEntity.id = image.id();
        imageEntity.type = image.type();
        imageEntity.objectKey = image.objectKey();
        imageEntity.bucket = image.bucket();
        imageEntity.url = image.url();
        return imageEntity;
    }
}
