package com.handwoong.rainbowletter.faq.service.port;

import com.handwoong.rainbowletter.faq.domain.FAQ;
import java.util.List;
import java.util.Optional;

public interface FAQRepository {
    FAQ save(FAQ faq);

    List<FAQ> saveAll(FAQ... faqs);

    Optional<FAQ> findById(Long id);

    List<FAQ> findAllByUser();

    List<FAQ> findAllByAdmin();

    void delete(FAQ faq);
}
