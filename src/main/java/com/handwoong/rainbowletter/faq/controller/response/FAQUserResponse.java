package com.handwoong.rainbowletter.faq.controller.response;

import com.handwoong.rainbowletter.faq.domain.FAQ;

public record FAQUserResponse(
        Long id,
        String summary,
        String detail
) {
    public static FAQUserResponse from(final FAQ faq) {
        return new FAQUserResponse(faq.id(), faq.summary(), faq.detail());
    }
}
