package com.handwoong.rainbowletter.letter.service.port;

import com.handwoong.rainbowletter.letter.controller.response.LetterBoxResponse;
import com.handwoong.rainbowletter.letter.controller.response.LetterResponse;
import com.handwoong.rainbowletter.letter.domain.Letter;
import com.handwoong.rainbowletter.member.domain.Email;
import java.time.LocalDate;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface LetterRepository {
    Letter save(Letter letter);

    Letter findByIdOrElseThrow(Long id);

    List<LetterBoxResponse> findAllLetterBoxByEmail(Email email);

    LetterResponse findLetterResponseByIdOrElseThrow(Email email, Long id);

    LetterResponse findLetterResponseByShareLinkOrElseThrow(String shareLink);

    Page<LetterResponse> findAdminAllLetterResponses(LocalDate startDate, LocalDate endDate, Pageable pageable);

    boolean existsByPet(Long petId);
}
