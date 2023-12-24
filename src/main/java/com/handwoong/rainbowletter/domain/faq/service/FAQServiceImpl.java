package com.handwoong.rainbowletter.domain.faq.service;

import com.handwoong.rainbowletter.domain.faq.dto.FAQAdminDto;
import com.handwoong.rainbowletter.domain.faq.dto.FAQAdminResponse;
import com.handwoong.rainbowletter.domain.faq.dto.FAQChangeSequenceRequest;
import com.handwoong.rainbowletter.domain.faq.dto.FAQDto;
import com.handwoong.rainbowletter.domain.faq.dto.FAQRequest;
import com.handwoong.rainbowletter.domain.faq.dto.FAQResponse;
import com.handwoong.rainbowletter.domain.faq.model.FAQ;
import com.handwoong.rainbowletter.domain.faq.repository.FAQRepository;
import com.handwoong.rainbowletter.exception.ErrorCode;
import com.handwoong.rainbowletter.exception.RainbowLetterException;
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
    public FAQAdminResponse findAll() {
        final List<FAQAdminDto> faqs = faqRepository.findAllByOrderBySortIndexAsc();
        return new FAQAdminResponse(faqs);
    }

    @Override
    @Transactional
    public void create(final FAQRequest request) {
        final FAQ faq = FAQ.create(request);
        faqRepository.save(faq);
    }

    @Override
    @Transactional
    public void edit(final Long faqId, final FAQRequest request) {
        final FAQ faq = faqRepository.findById(faqId)
                .orElseThrow(() -> new RainbowLetterException(ErrorCode.INVALID_FAQ_ID));
        faq.edit(request);
    }

    @Override
    @Transactional
    public void changeVisibility(final Long faqId) {
        final FAQ faq = faqRepository.findById(faqId)
                .orElseThrow(() -> new RainbowLetterException(ErrorCode.INVALID_FAQ_ID));
        faq.changeVisibility();
    }

    @Override
    @Transactional
    public void changeSequence(final Long faqId, final FAQChangeSequenceRequest request) {
        final FAQ faq = faqRepository.findById(faqId)
                .orElseThrow(() -> new RainbowLetterException(ErrorCode.INVALID_FAQ_ID));
        final FAQ targetFaq = faqRepository.findById(request.targetId())
                .orElseThrow(() -> new RainbowLetterException(ErrorCode.INVALID_FAQ_ID));
        faq.changeSequence(targetFaq);
    }

    @Override
    @Transactional
    public void delete(final Long faqId) {
        final FAQ faq = faqRepository.findById(faqId)
                .orElseThrow(() -> new RainbowLetterException(ErrorCode.INVALID_FAQ_ID));
        faqRepository.delete(faq);
    }
}