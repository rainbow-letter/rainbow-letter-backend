package com.handwoong.rainbowletter.letter.controller.port;

import com.handwoong.rainbowletter.letter.controller.response.LetterBoxResponse;
import com.handwoong.rainbowletter.letter.controller.response.LetterResponse;
import com.handwoong.rainbowletter.letter.domain.Letter;
import com.handwoong.rainbowletter.letter.domain.dto.LetterCreate;
import com.handwoong.rainbowletter.member.domain.Email;
import java.util.List;

public interface LetterService {
    Letter create(Long petId, LetterCreate request);

    List<LetterBoxResponse> findAllLetterBoxByEmail(Email email);

    LetterResponse findLetterById(Long id);
}
