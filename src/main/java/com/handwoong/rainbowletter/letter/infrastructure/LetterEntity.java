package com.handwoong.rainbowletter.letter.infrastructure;

import com.handwoong.rainbowletter.common.infrastructure.BaseEntity;
import com.handwoong.rainbowletter.image.infrastructure.ImageEntity;
import com.handwoong.rainbowletter.letter.domain.Content;
import com.handwoong.rainbowletter.letter.domain.Letter;
import com.handwoong.rainbowletter.letter.domain.LetterStatus;
import com.handwoong.rainbowletter.letter.domain.Summary;
import com.handwoong.rainbowletter.pet.infrastructure.PetEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import java.util.Objects;
import lombok.Getter;
import org.hibernate.Hibernate;

@Getter
@Entity
@Table(name = "letter")
public class LetterEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(length = 20)
    private String summary;

    @NotNull
    @Column(columnDefinition = "TEXT")
    private String content;

    @NotNull
    @Enumerated(EnumType.STRING)
    private LetterStatus status;

    @OneToOne(fetch = FetchType.LAZY, orphanRemoval = true)
    @JoinColumn(name = "image_id", referencedColumnName = "id")
    private ImageEntity imageEntity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pet_id", referencedColumnName = "id")
    private PetEntity petEntity;

    @OneToOne(fetch = FetchType.LAZY, orphanRemoval = true)
    @JoinColumn(name = "reply_id", referencedColumnName = "id")
    private ReplyEntity replyEntity;

    public static LetterEntity from(final Letter letter) {
        final LetterEntity letterEntity = new LetterEntity();
        letterEntity.id = letter.id();
        letterEntity.summary = letter.summary().toString();
        letterEntity.content = letter.content().toString();
        letterEntity.status = letter.status();
        letterEntity.imageEntity = Objects.nonNull(letter.image()) ? ImageEntity.from(letter.image()) : null;
        letterEntity.petEntity = PetEntity.from(letter.pet());
        letterEntity.replyEntity = Objects.nonNull(letter.reply()) ? ReplyEntity.from(letter.reply()) : null;
        return letterEntity;
    }

    public Letter toModel() {
        return Letter.builder()
                .id(id)
                .summary(new Summary(summary))
                .content(new Content(content))
                .status(status)
                .image(Objects.nonNull(imageEntity) ? imageEntity.toModel() : null)
                .pet(petEntity.toModel())
                .reply(Objects.nonNull(replyEntity) ? replyEntity.toModel() : null)
                .createdAt(getCreatedAt())
                .build();
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (Objects.isNull(obj) || Hibernate.getClass(this) != Hibernate.getClass(obj)) {
            return false;
        }
        LetterEntity entity = (LetterEntity) obj;
        return Objects.nonNull(id) && Objects.equals(id, entity.id);
    }

    @Override
    public int hashCode() {
        return Objects.nonNull(id) ? id.intValue() : 0;
    }
}
