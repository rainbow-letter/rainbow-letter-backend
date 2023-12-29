package com.handwoong.rainbowletter.faq.service;

import com.handwoong.rainbowletter.faq.controller.port.FAQService;
import com.handwoong.rainbowletter.faq.domain.FAQ;
import com.handwoong.rainbowletter.faq.domain.dto.FAQChangeSequence;
import com.handwoong.rainbowletter.faq.domain.dto.FAQCreate;
import com.handwoong.rainbowletter.faq.domain.dto.FAQUpdate;
import com.handwoong.rainbowletter.faq.exception.FAQResourceNotFoundException;
import com.handwoong.rainbowletter.faq.service.port.FAQRepository;
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
    public List<FAQ> findAllByUser() {
        return faqRepository.findAllByUser();
    }

    @Override
    public List<FAQ> findAllByAdmin() {
        return faqRepository.findAllByAdmin();
    }

    @Override
    @Transactional
    public FAQ create(final FAQCreate request) {
        final FAQ faq = FAQ.create(request);
        return faqRepository.save(faq);
    }

    @Override
    @Transactional
    public FAQ update(final Long id, final FAQUpdate request) {
        final FAQ faq = findByIdOrElseThrow(id);
        final FAQ updateFaq = faq.update(request);
        return faqRepository.save(updateFaq);
    }

    @Override
    @Transactional
    public FAQ changeVisibility(final Long id) {
        final FAQ faq = findByIdOrElseThrow(id);
        final FAQ updateFaq = faq.changeVisibility();
        return faqRepository.save(updateFaq);
    }

    @Override
    @Transactional
    public List<FAQ> changeSequence(final Long id, final FAQChangeSequence request) {
        final FAQ faq = findByIdOrElseThrow(id);
        final FAQ targetFaq = findByIdOrElseThrow(request.targetId());

        final Long tempSequence = faq.sequence();
        final FAQ updateFaq = faq.changeSequence(targetFaq.sequence());
        final FAQ updateTargetFaq = targetFaq.changeSequence(tempSequence);
        return faqRepository.saveAll(updateFaq, updateTargetFaq);
    }

    @Override
    @Transactional
    public void delete(final Long id) {
        final FAQ faq = findByIdOrElseThrow(id);
        faqRepository.delete(faq);
    }

    private FAQ findByIdOrElseThrow(final Long id) {
        return faqRepository.findById(id)
                .orElseThrow(() -> new FAQResourceNotFoundException(id));
    }
}
