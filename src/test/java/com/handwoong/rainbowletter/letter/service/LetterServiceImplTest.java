package com.handwoong.rainbowletter.letter.service;

import static org.assertj.core.api.Assertions.assertThat;

import com.handwoong.rainbowletter.letter.domain.Content;
import com.handwoong.rainbowletter.letter.domain.Letter;
import com.handwoong.rainbowletter.letter.domain.LetterStatus;
import com.handwoong.rainbowletter.letter.domain.Summary;
import com.handwoong.rainbowletter.letter.domain.dto.LetterCreate;
import com.handwoong.rainbowletter.mock.letter.LetterTestContainer;
import java.time.LocalDate;
import org.junit.jupiter.api.Test;

class LetterServiceImplTest {
    @Test
    void 편지를_생성한다() {
        // given
        final LetterCreate request = LetterCreate.builder()
                .summary(new Summary("콩아 잘 지내고 있니?"))
                .content(new Content("콩아 잘 지내고 있니? 너를 보낸지 얼마 안되었는데 벌써 너가 보고싶다."))
                .image(1L)
                .build();
        final LetterTestContainer testContainer = new LetterTestContainer();

        // when
        final Letter letter = testContainer.service.create(1L, request);

        // then
        assertThat(letter.id()).isEqualTo(1);
        assertThat(letter.summary()).hasToString("콩아 잘 지내고 있니?");
        assertThat(letter.content()).hasToString("콩아 잘 지내고 있니? 너를 보낸지 얼마 안되었는데 벌써 너가 보고싶다.");
        assertThat(letter.status()).isEqualTo(LetterStatus.REQUEST);
        assertThat(letter.pet()).isEqualTo(testContainer.pet);
        assertThat(letter.image()).isEqualTo(testContainer.image);
        assertThat(letter.reply()).isNull();
        assertThat(letter.createdAt()).isEqualTo(LocalDate.now().atStartOfDay());
    }
}
