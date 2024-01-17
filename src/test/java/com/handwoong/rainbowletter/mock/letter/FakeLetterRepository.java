package com.handwoong.rainbowletter.mock.letter;

import com.handwoong.rainbowletter.letter.controller.response.LetterAdminResponse;
import com.handwoong.rainbowletter.letter.controller.response.LetterBoxResponse;
import com.handwoong.rainbowletter.letter.controller.response.LetterResponse;
import com.handwoong.rainbowletter.letter.domain.Letter;
import com.handwoong.rainbowletter.letter.exception.LetterResourceNotFoundException;
import com.handwoong.rainbowletter.letter.exception.LetterShareLinkNotFoundException;
import com.handwoong.rainbowletter.letter.service.port.LetterRepository;
import com.handwoong.rainbowletter.member.domain.Email;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public class FakeLetterRepository implements LetterRepository {
    private final Map<Long, Letter> database = new HashMap<>();

    private Long sequence = 1L;

    @Override
    public Letter save(final Letter letter) {
        Long id = Objects.nonNull(letter.id()) ? letter.id() : sequence++;
        final Letter saveLetter = createLetter(id, letter);
        database.put(id, saveLetter);
        return saveLetter;
    }

    @Override
    public Letter findByIdOrElseThrow(final Long id) {
        return Optional.ofNullable(database.get(id))
                .orElseThrow(() -> new LetterResourceNotFoundException(id));
    }

    private Letter createLetter(final Long id, final Letter letter) {
        return Letter.builder()
                .id(id)
                .summary(letter.summary())
                .content(letter.content())
                .status(letter.status())
                .pet(letter.pet())
                .image(letter.image())
                .reply(letter.reply())
                .createdAt(LocalDate.now().atStartOfDay())
                .build();
    }

    @Override
    public List<LetterBoxResponse> findAllLetterBoxByEmail(final Email email) {
        return database.values().stream()
                .filter(letter -> letter.pet().member().email().equals(email))
                .map(LetterBoxResponse::from)
                .toList();
    }

    @Override
    public LetterResponse findLetterResponseByIdOrElseThrow(final Email email, final Long id) {
        final Letter findLetter = Optional.ofNullable(database.get(id))
                .filter(letter -> letter.pet().member().email().equals(email))
                .orElseThrow(() -> new LetterResourceNotFoundException(id));
        return LetterResponse.from(findLetter);
    }

    @Override
    public LetterResponse findLetterResponseByShareLinkOrElseThrow(final String shareLink) {
        final Letter findLetter = database.values().stream()
                .filter(letter -> letter.shareLink().equals(shareLink))
                .findAny()
                .orElseThrow(() -> new LetterShareLinkNotFoundException(shareLink));
        return LetterResponse.from(findLetter);
    }

    @Override
    public Page<LetterAdminResponse> findAdminAllLetterResponses(final LocalDate startDate,
                                                                 final LocalDate endDate,
                                                                 final Pageable pageable) {
        return null;
    }

    @Override
    public boolean existsByPet(final Long petId) {
        final List<Letter> result = database.values().stream()
                .filter(letter -> letter.pet().id().equals(petId))
                .toList();
        return result.size() > 1;
    }
}
