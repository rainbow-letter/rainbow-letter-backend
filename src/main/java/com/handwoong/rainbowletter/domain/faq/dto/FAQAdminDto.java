package com.handwoong.rainbowletter.domain.faq.dto;

public record FAQAdminDto(
        Long id,
        String summary,
        String detail,
        boolean visibility
) {
}
