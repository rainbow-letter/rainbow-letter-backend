package com.handwoong.rainbowletter.mock.letter;

import com.handwoong.rainbowletter.letter.domain.Letter;
import com.handwoong.rainbowletter.letter.service.port.LetterRepository;
import java.time.LocalDate;
import java.util.HashMap;
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
}
