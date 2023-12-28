package com.handwoong.rainbowletter.faq.controller.response;

import com.handwoong.rainbowletter.faq.domain.FAQ;

public record FAQAdminResponse(
        Long id,
        String summary,
        String detail,
        boolean visibility
) {
    public static FAQAdminResponse from(final FAQ faq) {
        return new FAQAdminResponse(faq.id(), faq.summary(), faq.detail(), faq.visibility());
    }
}
