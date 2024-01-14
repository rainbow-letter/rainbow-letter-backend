package com.handwoong.rainbowletter.letter.controller.port;

import com.handwoong.rainbowletter.letter.controller.response.LetterBoxResponse;
import com.handwoong.rainbowletter.letter.controller.response.LetterResponse;
import com.handwoong.rainbowletter.letter.domain.Letter;
import com.handwoong.rainbowletter.letter.domain.dto.LetterCreate;
import com.handwoong.rainbowletter.member.domain.Email;
import java.time.LocalDate;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface LetterService {
    Letter create(Long petId, LetterCreate request);

    List<LetterBoxResponse> findAllLetterBoxByEmail(Email email);

    LetterResponse findLetterById(Email email, Long id);

    Page<LetterResponse> findAllAdminLetters(LocalDate startDate, LocalDate endDate, Pageable pageable);

    LetterResponse findLetterByShareLink(String shareLink);
}
