package com.handwoong.rainbowletter.temporary.service.port;

import com.handwoong.rainbowletter.temporary.domain.Temporary;

public interface TemporaryRepository {
    Temporary save(Temporary temporary);

    Temporary findByIdAndMemberIdOrElseThrow(Long id, Long memberId);

    boolean existsByMemberId(Long memberId);

    Temporary findByMemberId(Long memberId);
}
