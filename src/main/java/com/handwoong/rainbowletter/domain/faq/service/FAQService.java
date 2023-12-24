package com.handwoong.rainbowletter.domain.faq.service;

import com.handwoong.rainbowletter.domain.faq.dto.FAQAdminResponse;
import com.handwoong.rainbowletter.domain.faq.dto.FAQChangeSequenceRequest;
import com.handwoong.rainbowletter.domain.faq.dto.FAQRequest;
import com.handwoong.rainbowletter.domain.faq.dto.FAQResponse;

public interface FAQService {
    FAQResponse findAllVisibilityTrue();

    FAQAdminResponse findAll();

    void create(final FAQRequest request);

    void edit(final Long faqId, final FAQRequest request);

    void changeVisibility(final Long faqId);

    void changeSequence(final Long faqId, final FAQChangeSequenceRequest request);

    void delete(final Long faqId);
}
