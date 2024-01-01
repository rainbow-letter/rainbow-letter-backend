package com.handwoong.rainbowletter.faq.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.handwoong.rainbowletter.faq.domain.FAQ;
import com.handwoong.rainbowletter.faq.domain.dto.FAQChangeSequence;
import com.handwoong.rainbowletter.faq.domain.dto.FAQCreate;
import com.handwoong.rainbowletter.faq.domain.dto.FAQUpdate;
import com.handwoong.rainbowletter.faq.exception.FAQResourceNotFoundException;
import com.handwoong.rainbowletter.mock.TestContainer;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class FAQServiceTest {
    @Test
    void 사용자용_FAQ_목록을_조회한다() {
        // given
        final TestContainer testContainer = new TestContainer();
        final FAQ faq1 = FAQ.builder()
                .summary("무지개 편지는 무슨 서비스인가요?")
                .detail("무지개 편지는 무지개 다리를 건넌 반려동물과 편지를 주고받는 서비스입니다.")
                .visibility(false)
                .sequence(0L)
                .build();
        final FAQ faq2 = FAQ.builder()
                .summary("답장은 언제 오나요?")
                .detail("편지를 보내고 1~2일 이내 도착해요.")
                .visibility(true)
                .sequence(1L)
                .build();
        testContainer.faqRepository.saveAll(faq1, faq2);

        // when
        final List<FAQ> faqs = testContainer.faqService.findAllByUser();

        // then
        assertThat(faqs).hasSize(1);
        assertThat(faqs.get(0).id()).isEqualTo(2);
        assertThat(faqs.get(0).summary()).isEqualTo("답장은 언제 오나요?");
        assertThat(faqs.get(0).detail()).isEqualTo("편지를 보내고 1~2일 이내 도착해요.");
        assertThat(faqs.get(0).sequence()).isNull();
    }

    @Test
    void 관리자용_FAQ_목록을_조회한다() {
        // given
        final TestContainer testContainer = new TestContainer();
        final FAQ faq1 = FAQ.builder()
                .summary("무지개 편지는 무슨 서비스인가요?")
                .detail("무지개 편지는 무지개 다리를 건넌 반려동물과 편지를 주고받는 서비스입니다.")
                .visibility(false)
                .sequence(0L)
                .build();
        final FAQ faq2 = FAQ.builder()
                .summary("답장은 언제 오나요?")
                .detail("편지를 보내고 1~2일 이내 도착해요.")
                .visibility(true)
                .sequence(1L)
                .build();
        testContainer.faqRepository.saveAll(faq1, faq2);

        // when
        final List<FAQ> faqs = testContainer.faqService.findAllByAdmin();

        // then
        assertThat(faqs).hasSize(2);
        assertThat(faqs.get(0).id()).isEqualTo(1);
        assertThat(faqs.get(0).summary()).isEqualTo("무지개 편지는 무슨 서비스인가요?");
        assertThat(faqs.get(0).detail()).isEqualTo("무지개 편지는 무지개 다리를 건넌 반려동물과 편지를 주고받는 서비스입니다.");
        assertThat(faqs.get(0).visibility()).isFalse();
        assertThat(faqs.get(0).sequence()).isNull();

        assertThat(faqs.get(1).id()).isEqualTo(2);
        assertThat(faqs.get(1).summary()).isEqualTo("답장은 언제 오나요?");
        assertThat(faqs.get(1).detail()).isEqualTo("편지를 보내고 1~2일 이내 도착해요.");
        assertThat(faqs.get(1).visibility()).isTrue();
        assertThat(faqs.get(1).sequence()).isNull();
    }

    @Test
    void FAQ를_생성한다() {
        // given
        final TestContainer testContainer = new TestContainer();
        final FAQCreate request = FAQCreate.builder()
                .summary("무지개 편지는 무슨 서비스인가요?")
                .detail("무지개 편지는 무지개 다리를 건넌 반려동물과 편지를 주고받는 서비스입니다.")
                .build();

        // when
        final FAQ faq = testContainer.faqService.create(request);

        // then
        assertThat(faq.id()).isEqualTo(1);
        assertThat(faq.summary()).isEqualTo("무지개 편지는 무슨 서비스인가요?");
        assertThat(faq.detail()).isEqualTo("무지개 편지는 무지개 다리를 건넌 반려동물과 편지를 주고받는 서비스입니다.");
        assertThat(faq.visibility()).isTrue();
        assertThat(faq.sequence()).isEqualTo(1);
    }

    @Test
    void FAQ를_업데이트한다() {
        // given
        final FAQ faq = FAQ.builder()
                .summary("무지개 편지는 무슨 서비스인가요?")
                .detail("무지개 편지는 무지개 다리를 건넌 반려동물과 편지를 주고받는 서비스입니다.")
                .visibility(true)
                .sequence(0L)
                .build();

        final TestContainer testContainer = new TestContainer();
        testContainer.faqRepository.save(faq);

        final FAQUpdate request = FAQUpdate.builder()
                .summary("답장이 온 건 어떻게 알 수 있나요?")
                .detail("답장이 도착하면 등록하신 이메일 주소로 메일을 보내드려요.")
                .build();

        // when
        final FAQ updateFaq = testContainer.faqService.update(1L, request);

        // then
        assertThat(updateFaq.id()).isEqualTo(1);
        assertThat(updateFaq.summary()).isEqualTo("답장이 온 건 어떻게 알 수 있나요?");
        assertThat(updateFaq.detail()).isEqualTo("답장이 도착하면 등록하신 이메일 주소로 메일을 보내드려요.");
        assertThat(updateFaq.visibility()).isTrue();
        assertThat(updateFaq.sequence()).isEqualTo(1);
    }

    @Test
    void ID로_FAQ를_찾지_못하면_업데이트에_실패하고_예외가_발생한다() {
        // given
        final TestContainer testContainer = new TestContainer();
        final FAQUpdate request = FAQUpdate.builder()
                .summary("답장이 온 건 어떻게 알 수 있나요?")
                .detail("답장이 도착하면 등록하신 이메일 주소로 메일을 보내드려요.")
                .build();

        // when
        // then
        assertThatThrownBy(() -> testContainer.faqService.update(1L, request))
                .isInstanceOf(FAQResourceNotFoundException.class)
                .hasMessage("해당 FAQ를 찾을 수 없습니다.");
    }

    @ParameterizedTest(name = "Visibility {index} : {0}")
    @ValueSource(booleans = {true, false})
    void FAQ의_보이기_상태를_변경한다(final boolean visibility) {
        // given
        final FAQ faq = FAQ.builder()
                .summary("무지개 편지는 무슨 서비스인가요?")
                .detail("무지개 편지는 무지개 다리를 건넌 반려동물과 편지를 주고받는 서비스입니다.")
                .visibility(visibility)
                .sequence(0L)
                .build();

        final TestContainer testContainer = new TestContainer();
        testContainer.faqRepository.save(faq);

        // when
        final FAQ updateFaq = testContainer.faqService.changeVisibility(1L);

        // then
        assertThat(updateFaq.id()).isEqualTo(1);
        assertThat(updateFaq.summary()).isEqualTo("무지개 편지는 무슨 서비스인가요?");
        assertThat(updateFaq.detail()).isEqualTo("무지개 편지는 무지개 다리를 건넌 반려동물과 편지를 주고받는 서비스입니다.");
        assertThat(updateFaq.visibility()).isEqualTo(!visibility);
        assertThat(updateFaq.sequence()).isEqualTo(1);
    }

    @Test
    void ID로_FAQ를_찾지_못하면_보이기_여부_변경에_실패하고_예외가_발생한다() {
        // given
        final TestContainer testContainer = new TestContainer();

        // when
        // then
        assertThatThrownBy(() -> testContainer.faqService.changeVisibility(1L))
                .isInstanceOf(FAQResourceNotFoundException.class)
                .hasMessage("해당 FAQ를 찾을 수 없습니다.");
    }

    @Test
    void FAQ의_순서를_변경한다() {
        // given
        final FAQ faq1 = FAQ.builder()
                .id(1L)
                .summary("무지개 편지는 무슨 서비스인가요?")
                .detail("무지개 편지는 무지개 다리를 건넌 반려동물과 편지를 주고받는 서비스입니다.")
                .visibility(true)
                .sequence(0L)
                .build();
        final FAQ faq2 = FAQ.builder()
                .id(2L)
                .summary("답장은 언제 오나요?")
                .detail("편지를 보내고 1~2일 이내 도착해요.")
                .visibility(true)
                .sequence(50L)
                .build();

        final TestContainer testContainer = new TestContainer();
        testContainer.faqRepository.saveAll(faq1, faq2);

        final FAQChangeSequence request = FAQChangeSequence.builder()
                .targetId(2L)
                .build();

        // when
        final List<FAQ> faqs = testContainer.faqService.changeSequence(1L, request);

        // then
        assertThat(faqs).hasSize(2);
        assertThat(faqs.get(0).id()).isEqualTo(1);
        assertThat(faqs.get(0).summary()).isEqualTo("무지개 편지는 무슨 서비스인가요?");
        assertThat(faqs.get(0).detail()).isEqualTo("무지개 편지는 무지개 다리를 건넌 반려동물과 편지를 주고받는 서비스입니다.");
        assertThat(faqs.get(0).visibility()).isTrue();
        assertThat(faqs.get(0).sequence()).isEqualTo(50);

        assertThat(faqs.get(1).id()).isEqualTo(2);
        assertThat(faqs.get(1).summary()).isEqualTo("답장은 언제 오나요?");
        assertThat(faqs.get(1).detail()).isEqualTo("편지를 보내고 1~2일 이내 도착해요.");
        assertThat(faqs.get(1).visibility()).isTrue();
        assertThat(faqs.get(1).sequence()).isZero();
    }

    @Test
    void FAQ의_순서_변경시_ID로_FAQ를_찾지_못하면_예외가_발생한다() {
        // given
        final TestContainer testContainer = new TestContainer();
        final FAQChangeSequence request = FAQChangeSequence.builder()
                .targetId(2L)
                .build();

        // when
        // then
        assertThatThrownBy(() -> testContainer.faqService.changeSequence(1L, request))
                .isInstanceOf(FAQResourceNotFoundException.class)
                .hasMessage("해당 FAQ를 찾을 수 없습니다.");
    }

    @Test
    void FAQ를_삭제한다() {
        // given
        final FAQ faq = FAQ.builder()
                .summary("무지개 편지는 무슨 서비스인가요?")
                .detail("무지개 편지는 무지개 다리를 건넌 반려동물과 편지를 주고받는 서비스입니다.")
                .visibility(true)
                .sequence(0L)
                .build();

        final TestContainer testContainer = new TestContainer();
        testContainer.faqRepository.save(faq);

        // when
        testContainer.faqService.delete(1L);
        final List<FAQ> faqs = testContainer.faqRepository.findAllByAdmin();

        // then
        assertThat(faqs).isEmpty();
    }

    @Test
    void FAQ_삭제시_ID로_FAQ를_찾지_못하면_예외가_발생한다() {
        // given
        final TestContainer testContainer = new TestContainer();

        // when
        // then
        assertThatThrownBy(() -> testContainer.faqService.delete(1L))
                .isInstanceOf(FAQResourceNotFoundException.class)
                .hasMessage("해당 FAQ를 찾을 수 없습니다.");
    }
}
