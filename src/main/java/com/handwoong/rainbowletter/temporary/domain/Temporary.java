package com.handwoong.rainbowletter.temporary.domain;

import com.handwoong.rainbowletter.common.service.port.UuidGenerator;
import com.handwoong.rainbowletter.temporary.domain.dto.TemporaryCreate;
import com.handwoong.rainbowletter.temporary.domain.dto.TemporaryUpdate;

import lombok.Builder;

@Builder
public record Temporary(
        Long id,
        Long memberId,
        Long petId,
        String sessionId,
        String content,
        TemporaryStatus status
) {

    public static Temporary create(final Long memberId,
                                   final TemporaryCreate temporaryCreate,
                                   final UuidGenerator uuidGenerator
    ) {
        return Temporary.builder()
                .memberId(memberId)
                .petId(temporaryCreate.petId())
                .sessionId(uuidGenerator.generate())
                .content(temporaryCreate.content())
                .status(TemporaryStatus.SAVE)
                .build();
    }

    public Temporary delete() {
        return Temporary.builder()
                .id(id)
                .memberId(memberId)
                .petId(petId)
                .sessionId(sessionId)
                .content(content)
                .status(TemporaryStatus.DELETE)
                .build();
    }

    public Temporary changeSession(final UuidGenerator uuidGenerator) {
        return Temporary.builder()
                .id(id)
                .memberId(memberId)
                .petId(petId)
                .sessionId(uuidGenerator.generate())
                .content(content)
                .status(status)
                .build();
    }

    public Temporary update(final TemporaryUpdate temporaryUpdate) {
        return Temporary.builder()
                .id(id)
                .memberId(memberId)
                .petId(petId)
                .sessionId(sessionId)
                .content(temporaryUpdate.content())
                .status(status)
                .build();
    }
}
