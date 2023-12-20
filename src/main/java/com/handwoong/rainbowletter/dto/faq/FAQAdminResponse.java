package com.handwoong.rainbowletter.dto.faq;

import java.util.List;

public record FAQAdminResponse(
        List<FAQAdminDto> faqs
) {
}
