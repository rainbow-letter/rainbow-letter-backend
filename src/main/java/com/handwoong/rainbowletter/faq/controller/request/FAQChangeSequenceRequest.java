package com.handwoong.rainbowletter.faq.controller.request;

import com.handwoong.rainbowletter.faq.domain.dto.FAQChangeSequence;

public record FAQChangeSequenceRequest(Long targetId) {
    public FAQChangeSequence toDto() {
        return FAQChangeSequence.builder()
                .targetId(targetId)
                .build();
    }
}
