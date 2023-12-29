package com.handwoong.rainbowletter.faq.domain.dto;

import lombok.Builder;

@Builder
public record FAQCreate(String summary, String detail) {
}
