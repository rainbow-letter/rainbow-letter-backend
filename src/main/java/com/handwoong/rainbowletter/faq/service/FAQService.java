package com.handwoong.rainbowletter.faq.service;

import com.handwoong.rainbowletter.faq.domain.FAQ;
import com.handwoong.rainbowletter.faq.domain.dto.FAQChangeSort;
import com.handwoong.rainbowletter.faq.domain.dto.FAQCreate;
import com.handwoong.rainbowletter.faq.domain.dto.FAQUpdate;
import java.util.List;

public interface FAQService {
    List<FAQ> findAllByUser();

    List<FAQ> findAllByAdmin();

    void create(final FAQCreate request);

    void update(final Long id, final FAQUpdate request);

    void changeVisibility(final Long id);

    void changeSortIndex(final Long id, final FAQChangeSort request);

    void delete(final Long id);
}
