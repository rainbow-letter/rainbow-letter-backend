package com.handwoong.rainbowletter.letter.controller.port;

import com.handwoong.rainbowletter.letter.domain.Letter;
import com.handwoong.rainbowletter.letter.domain.dto.LetterCreate;

public interface LetterService {
    Letter create(Long petId, LetterCreate request);
}
