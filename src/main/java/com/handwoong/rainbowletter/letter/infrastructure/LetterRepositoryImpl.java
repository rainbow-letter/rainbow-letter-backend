package com.handwoong.rainbowletter.letter.infrastructure;

import com.handwoong.rainbowletter.letter.domain.Letter;
import com.handwoong.rainbowletter.letter.service.port.LetterRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class LetterRepositoryImpl implements LetterRepository {
    private final LetterJpaRepository letterJpaRepository;

    @Override
    public Letter save(final Letter letter) {
        return letterJpaRepository.save(LetterEntity.from(letter)).toModel();
    }
}
