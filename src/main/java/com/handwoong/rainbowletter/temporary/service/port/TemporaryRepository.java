package com.handwoong.rainbowletter.temporary.service.port;

import com.handwoong.rainbowletter.temporary.domain.Temporary;

public interface TemporaryRepository {
    Temporary save(Temporary temporary);

    Temporary findByIdAndMemberIdOrElseThrow(Long id, Long memberId);

    boolean existsByMemberIdAndPetId(Long memberId, Long petId);

    Temporary findByMemberIdAndPetId(Long memberId, Long petId);
}
