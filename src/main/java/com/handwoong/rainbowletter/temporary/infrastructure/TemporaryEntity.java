package com.handwoong.rainbowletter.temporary.infrastructure;

import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;

import com.handwoong.rainbowletter.common.infrastructure.BaseEntity;
import com.handwoong.rainbowletter.temporary.domain.Temporary;
import com.handwoong.rainbowletter.temporary.domain.TemporaryStatus;

import lombok.Getter;

@Getter
@Entity
@Table(name = "temporary")
public class TemporaryEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private Long memberId;

    @NotNull
    private Long petId;

    @NotNull
    private String sessionId;

    @NotNull
    @Column(columnDefinition = "TEXT")
    private String content;

    @NotNull
    private TemporaryStatus status;

    public static TemporaryEntity from(final Temporary temporary) {
        final TemporaryEntity temporaryEntity = new TemporaryEntity();
        temporaryEntity.id = temporary.id();
        temporaryEntity.memberId = temporary.memberId();
        temporaryEntity.petId = temporary.petId();
        temporaryEntity.sessionId = temporary.sessionId();
        temporaryEntity.content = temporary.content();
        temporaryEntity.status = temporary.status();
        return temporaryEntity;
    }

    public Temporary toModel() {
        return Temporary.builder()
                .id(id)
                .memberId(memberId)
                .petId(petId)
                .sessionId(sessionId)
                .content(content)
                .status(status)
                .build();
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof final TemporaryEntity that)) {
            return false;
        }
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
