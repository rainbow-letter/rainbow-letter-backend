package com.handwoong.rainbowletter.temporary.infrastructure;

import org.springframework.data.jpa.repository.JpaRepository;

import com.handwoong.rainbowletter.temporary.domain.TemporaryStatus;

public interface TemporaryJpaRepository extends JpaRepository<TemporaryEntity, Long> {

    boolean existsByMemberIdAndStatus(Long memberId, TemporaryStatus status);
}
