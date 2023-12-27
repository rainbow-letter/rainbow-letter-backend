package com.handwoong.rainbowletter.faq.controller.response;

import com.handwoong.rainbowletter.faq.domain.dto.FAQDto;
import java.util.List;

public record FAQResponse(
        List<FAQDto> faqs
) {
}
