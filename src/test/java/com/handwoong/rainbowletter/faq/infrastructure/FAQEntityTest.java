package com.handwoong.rainbowletter.faq.infrastructure;

import static org.assertj.core.api.Assertions.assertThat;

import com.handwoong.rainbowletter.faq.domain.FAQ;
import org.junit.jupiter.api.Test;

class FAQEntityTest {
    @Test
    void FAQ_도메인으로_엔티티를_생성한다() {
        // given
        final FAQ faq = FAQ.builder()
                .id(1L)
                .summary("무지개 편지는 무슨 서비스인가요?")
                .detail("무지개 편지는 무지개 다리를 건넌 반려동물과 편지를 주고받는 서비스입니다.")
                .visibility(true)
                .sequence(0L)
                .build();

        // when
        final FAQEntity faqEntity = FAQEntity.from(faq);

        // then
        assertThat(faqEntity.getId()).isEqualTo(1);
        assertThat(faqEntity.getSummary()).isEqualTo("무지개 편지는 무슨 서비스인가요?");
        assertThat(faqEntity.getDetail()).isEqualTo("무지개 편지는 무지개 다리를 건넌 반려동물과 편지를 주고받는 서비스입니다.");
        assertThat(faqEntity.isVisibility()).isTrue();
        assertThat(faqEntity.getSequence()).isZero();
    }

    @Test
    void 엔티티로_FAQ_도메인을_생성한다() {
        // given
        final FAQ faq = FAQ.builder()
                .id(1L)
                .summary("무지개 편지는 무슨 서비스인가요?")
                .detail("무지개 편지는 무지개 다리를 건넌 반려동물과 편지를 주고받는 서비스입니다.")
                .visibility(true)
                .sequence(0L)
                .build();
        final FAQEntity faqEntity = FAQEntity.from(faq);

        // when
        final FAQ convertFaq = faqEntity.toModel();

        // then
        assertThat(convertFaq.id()).isEqualTo(1);
        assertThat(convertFaq.summary()).isEqualTo("무지개 편지는 무슨 서비스인가요?");
        assertThat(convertFaq.detail()).isEqualTo("무지개 편지는 무지개 다리를 건넌 반려동물과 편지를 주고받는 서비스입니다.");
        assertThat(convertFaq.visibility()).isTrue();
        assertThat(convertFaq.sequence()).isZero();
    }
}
