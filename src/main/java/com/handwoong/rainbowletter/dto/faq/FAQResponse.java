package com.handwoong.rainbowletter.dto.faq;

import java.util.List;

public record FAQResponse(
        List<FAQDto> faqs
) {
}
