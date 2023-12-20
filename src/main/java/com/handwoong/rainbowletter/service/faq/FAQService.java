package com.handwoong.rainbowletter.service.faq;

import com.handwoong.rainbowletter.dto.faq.FAQAdminResponse;
import com.handwoong.rainbowletter.dto.faq.FAQChangeSequenceRequest;
import com.handwoong.rainbowletter.dto.faq.FAQRequest;
import com.handwoong.rainbowletter.dto.faq.FAQResponse;

public interface FAQService {
    FAQResponse findAllVisibilityTrue();

    FAQAdminResponse findAll();

    void create(final FAQRequest request);

    void edit(final Long faqId, final FAQRequest request);

    void changeVisibility(final Long faqId);

    void changeSequence(final Long faqId, final FAQChangeSequenceRequest request);

    void delete(final Long faqId);
}
