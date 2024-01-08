package com.handwoong.rainbowletter.letter.service.port;

import com.handwoong.rainbowletter.letter.controller.response.LetterBoxResponse;
import com.handwoong.rainbowletter.letter.controller.response.LetterResponse;
import com.handwoong.rainbowletter.letter.domain.Letter;
import com.handwoong.rainbowletter.member.domain.Email;
import java.util.List;

public interface LetterRepository {
    Letter save(Letter letter);

    Letter findByIdOrElseThrow(Long id);

    List<LetterBoxResponse> findAllLetterBoxByEmail(Email email);

    LetterResponse findLetterResponseByIdOrElseThrow(Email email, Long id);

    boolean existsByPet(Long petId);
}
