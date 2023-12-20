package com.handwoong.rainbowletter.service.faq;

import com.handwoong.rainbowletter.domain.faq.FAQ;
import com.handwoong.rainbowletter.dto.faq.FAQCreateRequest;
import com.handwoong.rainbowletter.dto.faq.FAQDto;
import com.handwoong.rainbowletter.dto.faq.FAQResponse;
import com.handwoong.rainbowletter.repository.faq.FAQRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class FAQServiceImpl implements FAQService {
    private final FAQRepository faqRepository;

    @Override
    public FAQResponse findAllVisibilityTrue() {
        final List<FAQDto> faqs = faqRepository.findAllByVisibilityTrueOrderBySortIndexAsc();
        return new FAQResponse(faqs);
    }

    @Override
    @Transactional
    public void create(final FAQCreateRequest request) {
        final FAQ faq = FAQ.create(request);
        faqRepository.save(faq);
    }
}
