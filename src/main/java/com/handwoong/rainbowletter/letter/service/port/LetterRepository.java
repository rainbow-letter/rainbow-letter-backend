package com.handwoong.rainbowletter.letter.service.port;

import com.handwoong.rainbowletter.letter.controller.response.LetterBoxResponse;
import com.handwoong.rainbowletter.letter.domain.Letter;
import com.handwoong.rainbowletter.member.domain.Email;
import java.util.List;

public interface LetterRepository {
    Letter save(Letter letter);

    List<LetterBoxResponse> findAllLetterBoxByEmail(Email email);
}
