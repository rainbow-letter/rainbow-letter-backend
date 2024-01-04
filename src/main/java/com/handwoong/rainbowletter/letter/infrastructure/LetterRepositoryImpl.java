package com.handwoong.rainbowletter.letter.infrastructure;

import com.handwoong.rainbowletter.letter.service.port.LetterRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class LetterRepositoryImpl implements LetterRepository {
    private final LetterJpaRepository letterJpaRepository;
}
