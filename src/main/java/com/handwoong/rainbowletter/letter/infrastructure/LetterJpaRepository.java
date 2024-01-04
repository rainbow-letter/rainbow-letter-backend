package com.handwoong.rainbowletter.letter.infrastructure;

import org.springframework.data.jpa.repository.JpaRepository;

public interface LetterJpaRepository extends JpaRepository<LetterEntity, Long> {
}
