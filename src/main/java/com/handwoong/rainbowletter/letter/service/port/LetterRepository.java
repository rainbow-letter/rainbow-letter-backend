package com.handwoong.rainbowletter.letter.service.port;

import com.handwoong.rainbowletter.letter.domain.Letter;

public interface LetterRepository {
    Letter save(Letter letter);
}
