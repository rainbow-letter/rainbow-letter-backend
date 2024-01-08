package com.handwoong.rainbowletter.mock.letter;

import com.handwoong.rainbowletter.letter.controller.response.LetterBoxResponse;
import com.handwoong.rainbowletter.letter.controller.response.LetterResponse;
import com.handwoong.rainbowletter.letter.domain.Letter;
import com.handwoong.rainbowletter.letter.exception.LetterResourceNotFoundException;
import com.handwoong.rainbowletter.letter.service.port.LetterRepository;
import com.handwoong.rainbowletter.member.domain.Email;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

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
    public LetterResponse findLetterResponseByIdOrElseThrow(final Long id) {
        final Letter letter = database.get(id);
        if (Objects.isNull(letter)) {
            throw new LetterResourceNotFoundException(id);
        }
        return LetterResponse.from(letter);
    }

    @Override
    public boolean existsByPet(final Long petId) {
        final List<Letter> result = database.values().stream()
                .filter(letter -> letter.pet().id().equals(petId))
                .toList();
        return result.size() > 1;
    }
}
