package com.handwoong.rainbowletter.faq.controller.port;

import com.handwoong.rainbowletter.faq.domain.FAQ;
import com.handwoong.rainbowletter.faq.domain.dto.FAQChangeSequence;
import com.handwoong.rainbowletter.faq.domain.dto.FAQCreate;
import com.handwoong.rainbowletter.faq.domain.dto.FAQUpdate;
import java.util.List;

public interface FAQService {
    List<FAQ> findAllByUser();

    List<FAQ> findAllByAdmin();

    FAQ create(FAQCreate request);

    FAQ update(Long id, FAQUpdate request);

    FAQ changeVisibility(Long id);

    List<FAQ> changeSequence(Long id, FAQChangeSequence request);

    void delete(Long id);
}
