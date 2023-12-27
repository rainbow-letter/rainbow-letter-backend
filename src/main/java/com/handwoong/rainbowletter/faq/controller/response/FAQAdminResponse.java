package com.handwoong.rainbowletter.faq.controller.response;

import com.handwoong.rainbowletter.faq.domain.dto.FAQAdminDto;
import java.util.List;

public record FAQAdminResponse(
        List<FAQAdminDto> faqs
) {
}
