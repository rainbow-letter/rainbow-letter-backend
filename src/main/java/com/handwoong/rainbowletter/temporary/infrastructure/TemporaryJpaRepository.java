package com.handwoong.rainbowletter.temporary.infrastructure;

import org.springframework.data.jpa.repository.JpaRepository;

public interface TemporaryJpaRepository extends JpaRepository<TemporaryEntity, Long> {

    boolean existsByMemberIdAndPetId(Long memberId, Long petId);
}
