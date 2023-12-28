package com.handwoong.rainbowletter.faq.domain;

import com.handwoong.rainbowletter.faq.domain.dto.FAQCreate;
import com.handwoong.rainbowletter.faq.domain.dto.FAQUpdate;
import lombok.Builder;

@Builder
public record FAQ(
        Long id,
        String summary,
        String detail,
        boolean visibility,
        Long sortIndex
) {
    public static FAQ create(final FAQCreate request) {
        return FAQ.builder()
                .summary(request.summary())
                .detail(request.detail())
                .visibility(true)
                .build();
    }

    public FAQ update(final FAQUpdate request) {
        return FAQ.builder()
                .id(id)
                .summary(request.summary())
                .detail(request.detail())
                .visibility(visibility)
                .sortIndex(sortIndex)
                .build();
    }

    public FAQ changeVisibility() {
        return FAQ.builder()
                .id(id)
                .summary(summary)
                .detail(detail)
                .visibility(!visibility)
                .sortIndex(sortIndex)
                .build();
    }

    public FAQ changeSortIndex(final Long sortIndex) {
        return FAQ.builder()
                .id(id)
                .summary(summary)
                .detail(detail)
                .visibility(visibility)
                .sortIndex(sortIndex)
                .build();
    }
}
