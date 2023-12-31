package com.handwoong.rainbowletter.faq.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.handwoong.rainbowletter.faq.domain.dto.FAQCreate;
import com.handwoong.rainbowletter.faq.domain.dto.FAQUpdate;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class FAQTest {
    @Test
    void FAQ를_생성한다() {
        // given
        final FAQCreate request = FAQCreate.builder()
                .summary("무지개 편지는 무슨 서비스인가요?")
                .detail("무지개 편지는 무지개 다리를 건넌 반려동물과 편지를 주고받는 서비스입니다.")
                .build();

        // when
        final FAQ faq = FAQ.create(request);

        // then
        assertThat(faq.id()).isNull();
        assertThat(faq.summary()).isEqualTo("무지개 편지는 무슨 서비스인가요?");
        assertThat(faq.detail()).isEqualTo("무지개 편지는 무지개 다리를 건넌 반려동물과 편지를 주고받는 서비스입니다.");
        assertThat(faq.visibility()).isTrue();
        assertThat(faq.sequence()).isNull();
    }

    @Test
    void FAQ를_업데이트한다() {
        // given
        final FAQ faq = FAQ.builder()
                .id(1L)
                .summary("무지개 편지는 무슨 서비스인가요?")
                .detail("무지개 편지는 무지개 다리를 건넌 반려동물과 편지를 주고받는 서비스입니다.")
                .visibility(true)
                .sequence(0L)
                .build();

        final FAQUpdate request = FAQUpdate.builder()
                .summary("답장은 언제 오나요?")
                .detail("편지를 보내고 1~2일 이내 도착해요.")
                .build();

        // when
        final FAQ updateFaq = faq.update(request);

        // then
        assertThat(updateFaq.id()).isEqualTo(1);
        assertThat(updateFaq.summary()).isEqualTo("답장은 언제 오나요?");
        assertThat(updateFaq.detail()).isEqualTo("편지를 보내고 1~2일 이내 도착해요.");
        assertThat(updateFaq.visibility()).isTrue();
        assertThat(updateFaq.sequence()).isZero();
    }

    @ParameterizedTest(name = "Visibility {index} : {0}")
    @ValueSource(booleans = {true, false})
    void FAQ_보이기_여부를_변경한다(final boolean visibility) {
        // given
        final FAQ faq = FAQ.builder()
                .id(1L)
                .summary("무지개 편지는 무슨 서비스인가요?")
                .detail("무지개 편지는 무지개 다리를 건넌 반려동물과 편지를 주고받는 서비스입니다.")
                .visibility(visibility)
                .sequence(0L)
                .build();

        // when
        final FAQ updateFaq = faq.changeVisibility();

        // then
        assertThat(updateFaq.id()).isEqualTo(1);
        assertThat(updateFaq.summary()).isEqualTo("무지개 편지는 무슨 서비스인가요?");
        assertThat(updateFaq.detail()).isEqualTo("무지개 편지는 무지개 다리를 건넌 반려동물과 편지를 주고받는 서비스입니다.");
        assertThat(updateFaq.visibility()).isEqualTo(!visibility);
        assertThat(updateFaq.sequence()).isZero();
    }

    @Test
    void FAQ_정렬을_위한_SEQUENCE를_변경한다() {
        // given
        final FAQ faq = FAQ.builder()
                .id(1L)
                .summary("무지개 편지는 무슨 서비스인가요?")
                .detail("무지개 편지는 무지개 다리를 건넌 반려동물과 편지를 주고받는 서비스입니다.")
                .visibility(true)
                .sequence(0L)
                .build();

        // when
        final FAQ updateFaq = faq.changeSequence(10L);

        // then
        assertThat(updateFaq.id()).isEqualTo(1);
        assertThat(updateFaq.summary()).isEqualTo("무지개 편지는 무슨 서비스인가요?");
        assertThat(updateFaq.detail()).isEqualTo("무지개 편지는 무지개 다리를 건넌 반려동물과 편지를 주고받는 서비스입니다.");
        assertThat(updateFaq.visibility()).isTrue();
        assertThat(updateFaq.sequence()).isEqualTo(10);
    }
}