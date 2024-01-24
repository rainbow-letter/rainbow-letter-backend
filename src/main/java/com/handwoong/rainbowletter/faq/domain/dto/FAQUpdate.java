package com.handwoong.rainbowletter.faq.domain.dto;

import lombok.Builder;

@Builder
public record FAQUpdate(String summary, String detail) {
}
