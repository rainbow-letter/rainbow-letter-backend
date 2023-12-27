package com.handwoong.rainbowletter.faq.domain.dto;

public record FAQAdminDto(
        Long id,
        String summary,
        String detail,
        boolean visibility
) {
}
