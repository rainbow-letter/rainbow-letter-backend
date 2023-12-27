package com.handwoong.rainbowletter.image.infrastructure;

import com.handwoong.rainbowletter.common.infrastructure.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Image extends BaseEntity {
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

    public Image(final String type, final String objectKey, final String bucket, final String url) {
        this.type = type;
        this.objectKey = objectKey;
        this.bucket = bucket;
        this.url = url;
    }
}
