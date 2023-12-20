package com.handwoong.rainbowletter.service.faq;

import com.handwoong.rainbowletter.dto.faq.FAQCreateRequest;
import com.handwoong.rainbowletter.dto.faq.FAQResponse;

public interface FAQService {
    FAQResponse findAllVisibilityTrue();

    void create(final FAQCreateRequest request);
}
