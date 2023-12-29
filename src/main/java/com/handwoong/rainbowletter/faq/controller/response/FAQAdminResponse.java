package com.handwoong.rainbowletter.faq.controller.response;

import com.handwoong.rainbowletter.faq.domain.FAQ;
import lombok.Builder;

@Builder
public record FAQAdminResponse(Long id, String summary, String detail, boolean visibility) {
    public static FAQAdminResponse from(final FAQ faq) {
        return FAQAdminResponse.builder()
                .id(faq.id())
                .summary(faq.summary())
                .detail(faq.detail())
                .visibility(faq.visibility())
                .build();
    }
}
