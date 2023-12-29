package com.handwoong.rainbowletter.faq.controller.response;

import com.handwoong.rainbowletter.faq.domain.FAQ;
import java.util.List;
import lombok.Builder;

@Builder
public record FAQUserResponses(List<FAQUserResponse> faqs) {
    public static FAQUserResponses from(final List<FAQ> faqs) {
        final List<FAQUserResponse> userFaqs = faqs.stream()
                .map(FAQUserResponse::from)
                .toList();
        return FAQUserResponses.builder()
                .faqs(userFaqs)
                .build();
    }
}
