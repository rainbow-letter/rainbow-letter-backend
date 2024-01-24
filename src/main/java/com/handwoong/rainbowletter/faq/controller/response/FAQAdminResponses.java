package com.handwoong.rainbowletter.faq.controller.response;

import com.handwoong.rainbowletter.faq.domain.FAQ;
import java.util.List;
import lombok.Builder;

@Builder
public record FAQAdminResponses(List<FAQAdminResponse> faqs) {
    public static FAQAdminResponses from(final List<FAQ> faqs) {
        final List<FAQAdminResponse> adminFaqs = faqs.stream()
                .map(FAQAdminResponse::from)
                .toList();
        return FAQAdminResponses.builder()
                .faqs(adminFaqs)
                .build();
    }
}
