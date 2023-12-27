package com.handwoong.rainbowletter.faq.service;

import com.handwoong.rainbowletter.faq.controller.response.FAQAdminResponse;
import com.handwoong.rainbowletter.faq.domain.dto.FAQChangeSequenceRequest;
import com.handwoong.rainbowletter.faq.domain.dto.FAQRequest;
import com.handwoong.rainbowletter.faq.controller.response.FAQResponse;

public interface FAQService {
    FAQResponse findAllVisibilityTrue();

    FAQAdminResponse findAll();

    void create(final FAQRequest request);

    void edit(final Long faqId, final FAQRequest request);

    void changeVisibility(final Long faqId);

    void changeSequence(final Long faqId, final FAQChangeSequenceRequest request);

    void delete(final Long faqId);
}
