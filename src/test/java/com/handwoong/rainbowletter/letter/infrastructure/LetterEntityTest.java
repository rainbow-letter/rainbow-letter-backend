package com.handwoong.rainbowletter.letter.infrastructure;

import static org.assertj.core.api.Assertions.assertThat;

import com.handwoong.rainbowletter.image.infrastructure.ImageEntity;
import com.handwoong.rainbowletter.letter.domain.Content;
import com.handwoong.rainbowletter.letter.domain.Letter;
import com.handwoong.rainbowletter.letter.domain.LetterStatus;
import com.handwoong.rainbowletter.letter.domain.Summary;
import com.handwoong.rainbowletter.mock.letter.LetterTestContainer;
import com.handwoong.rainbowletter.pet.infrastructure.PetEntity;
import org.junit.jupiter.api.Test;

class LetterEntityTest {
    @Test
    void 편지_도메인으로_엔티티를_생성한다() {
        // given
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

        // when
        final LetterEntity letterEntity = LetterEntity.from(letter);

        // then
        assertThat(letterEntity.getId()).isEqualTo(1);
        assertThat(letterEntity.getSummary()).isEqualTo("콩아 잘 지내고 있니?");
        assertThat(letterEntity.getContent()).isEqualTo("콩아 잘 지내고 있니? 너를 보낸지 얼마 안되었는데 벌써 너가 보고싶다.");
        assertThat(letterEntity.getStatus()).isEqualTo(LetterStatus.REQUEST);
        assertThat(letterEntity.getPetEntity()).isEqualTo(PetEntity.from(testContainer.pet));
        assertThat(letterEntity.getImageEntity()).isEqualTo(ImageEntity.from(testContainer.image));
        assertThat(letterEntity.getReplyEntity()).isNull();
    }

    @Test
    void 엔티티로_편지_도메인을_생성한다() {
        // given
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

        final LetterEntity letterEntity = LetterEntity.from(letter);

        // when
        final Letter convertLetter = letterEntity.toModel();

        // then
        assertThat(convertLetter.id()).isEqualTo(1);
        assertThat(convertLetter.summary()).hasToString("콩아 잘 지내고 있니?");
        assertThat(convertLetter.content()).hasToString("콩아 잘 지내고 있니? 너를 보낸지 얼마 안되었는데 벌써 너가 보고싶다.");
        assertThat(convertLetter.status()).isEqualTo(LetterStatus.REQUEST);
        assertThat(convertLetter.pet()).isEqualTo(testContainer.pet);
        assertThat(convertLetter.image()).isEqualTo(testContainer.image);
        assertThat(convertLetter.reply()).isNull();
        assertThat(convertLetter.createdAt()).isNull();
    }
}
