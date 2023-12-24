package com.handwoong.rainbowletter.domain.faq.dto;

import java.util.List;

public record FAQAdminResponse(
        List<FAQAdminDto> faqs
) {
}
