package com.handwoong.rainbowletter.faq.controller.response;

import com.handwoong.rainbowletter.faq.domain.FAQ;
import lombok.Builder;

@Builder
public record FAQUserResponse(Long id, String summary, String detail) {
    public static FAQUserResponse from(final FAQ faq) {
        return FAQUserResponse.builder()
                .id(faq.id())
                .summary(faq.summary())
                .detail(faq.detail())
                .build();
    }
}
