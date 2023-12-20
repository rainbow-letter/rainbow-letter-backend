package com.handwoong.rainbowletter.dto.faq;

public record FAQAdminDto(
        Long id,
        String summary,
        String detail,
        boolean visibility
) {
}
