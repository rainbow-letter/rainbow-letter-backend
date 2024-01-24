package com.handwoong.rainbowletter.letter.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.handwoong.rainbowletter.image.controller.response.ImageResponse;
import com.handwoong.rainbowletter.letter.controller.response.LetterBoxResponse;
import com.handwoong.rainbowletter.letter.controller.response.LetterPetResponse;
import com.handwoong.rainbowletter.letter.controller.response.LetterResponse;
import com.handwoong.rainbowletter.letter.domain.Content;
import com.handwoong.rainbowletter.letter.domain.Letter;
import com.handwoong.rainbowletter.letter.domain.LetterStatus;
import com.handwoong.rainbowletter.letter.domain.Summary;
import com.handwoong.rainbowletter.letter.domain.dto.LetterCreate;
import com.handwoong.rainbowletter.letter.exception.LetterResourceNotFoundException;
import com.handwoong.rainbowletter.member.domain.Email;
import com.handwoong.rainbowletter.mock.letter.LetterTestContainer;
import java.time.LocalDate;
import java.util.List;
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

    @Test
    void 편지함의_편지를_조회한다() {
        // given
        final LetterTestContainer testContainer = new LetterTestContainer();
        final Email email = new Email("handwoong@gmail.com");
        final Letter letter = Letter.builder()
                .id(1L)
                .summary(new Summary("콩아 잘 지내고 있니?"))
                .content(new Content("콩아 잘 지내고 있니? 너를 보낸지 얼마 안되었는데 벌써 너가 보고싶다."))
                .status(LetterStatus.REQUEST)
                .pet(testContainer.pet)
                .image(testContainer.image)
                .reply(null)
                .build();
        testContainer.repository.save(letter);

        // when
        final List<LetterBoxResponse> result = testContainer.service.findAllLetterBoxByEmail(email);

        // then
        assertThat(result).hasSize(1);
        assertThat(result.get(0).id()).isEqualTo(1);
        assertThat(result.get(0).summary()).hasToString("콩아 잘 지내고 있니?");
        assertThat(result.get(0).status()).isEqualTo(LetterStatus.REQUEST);
        assertThat(result.get(0).petName()).isEqualTo("콩이");
        assertThat(result.get(0).createdAt()).isEqualTo(LocalDate.now().atStartOfDay());
    }

    @Test
    void ID로_편지를_조회한다() {
        // given
        final Email email = new Email("handwoong@gmail.com");
        final LetterTestContainer testContainer = new LetterTestContainer();
        final Letter letter = Letter.builder()
                .id(1L)
                .summary(new Summary("콩아 잘 지내고 있니?"))
                .content(new Content("콩아 잘 지내고 있니? 너를 보낸지 얼마 안되었는데 벌써 너가 보고싶다."))
                .status(LetterStatus.REQUEST)
                .pet(testContainer.pet)
                .image(testContainer.image)
                .reply(null)
                .build();
        testContainer.repository.save(letter);

        // when
        final LetterResponse result = testContainer.service.findLetterById(email, 1L);

        // then
        assertThat(result.id()).isEqualTo(1);
        assertThat(result.summary()).hasToString("콩아 잘 지내고 있니?");
        assertThat(result.content()).hasToString("콩아 잘 지내고 있니? 너를 보낸지 얼마 안되었는데 벌써 너가 보고싶다.");
        assertThat(result.pet()).isEqualTo(LetterPetResponse.from(testContainer.pet));
        assertThat(result.image()).isEqualTo(ImageResponse.from(testContainer.image));
        assertThat(result.createdAt()).isEqualTo(LocalDate.now().atStartOfDay());
    }

    @Test
    void ID로_편지_조회시_편지를_찾지_못하면_예외가_발생한다() {
        // given
        final Email email = new Email("handwoong@gmail.com");
        final LetterTestContainer testContainer = new LetterTestContainer();

        // when
        // then
        assertThatThrownBy(() -> testContainer.service.findLetterById(email, 1L))
                .isInstanceOf(LetterResourceNotFoundException.class)
                .hasMessage("해당 편지를 찾을 수 없습니다.");
    }
}
