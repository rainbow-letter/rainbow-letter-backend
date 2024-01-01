package com.handwoong.rainbowletter.mock.faq;

import com.handwoong.rainbowletter.faq.domain.FAQ;
import com.handwoong.rainbowletter.faq.service.port.FAQRepository;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

public class FakeFaqRepository implements FAQRepository {
    private final Map<Long, FAQ> database = new HashMap<>();

    private Long sequence = 1L;

    @Override
    public FAQ save(final FAQ faq) {
        final boolean idExists = Objects.nonNull(faq.id());
        final Long id = idExists ? faq.id() : sequence;
        final Long newSequence = idExists ? faq.sequence() : sequence;

        final FAQ saveFaq = createFAQ(id, newSequence, faq);
        database.put(id, saveFaq);
        if (!idExists) {
            sequence++;
        }
        return saveFaq;
    }

    private FAQ createFAQ(final Long id, final Long sequence, final FAQ faq) {
        return FAQ.builder()
                .id(id)
                .summary(faq.summary())
                .detail(faq.detail())
                .visibility(faq.visibility())
                .sequence(sequence)
                .build();
    }

    @Override
    public List<FAQ> saveAll(final FAQ... faqs) {
        final List<FAQ> list = new ArrayList<>();
        for (FAQ faq : faqs) {
            final FAQ savedFaq = save(faq);
            list.add(savedFaq);
        }
        return list;
    }

    @Override
    public Optional<FAQ> findById(final Long id) {
        return Optional.ofNullable(database.get(id));
    }

    @Override
    public List<FAQ> findAllByUser() {
        return database.values().stream()
                .filter(FAQ::visibility)
                .map(faq ->
                        FAQ.builder()
                                .id(faq.id())
                                .summary(faq.summary())
                                .detail(faq.detail())
                                .build()
                )
                .toList();
    }

    @Override
    public List<FAQ> findAllByAdmin() {
        return database.values().stream()
                .map(faq ->
                        FAQ.builder()
                                .id(faq.id())
                                .summary(faq.summary())
                                .detail(faq.detail())
                                .visibility(faq.visibility())
                                .build()
                )
                .toList();
    }

    @Override
    public void delete(final FAQ faq) {
        database.remove(faq.id());
    }
}
