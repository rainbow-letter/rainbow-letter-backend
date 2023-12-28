package com.handwoong.rainbowletter.faq.infrastructure;

import com.handwoong.rainbowletter.faq.controller.response.FAQAdminResponse;
import com.handwoong.rainbowletter.faq.controller.response.FAQUserResponse;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FAQJpaRepository extends JpaRepository<FAQEntity, Long> {
    List<FAQUserResponse> findAllByVisibilityTrueOrderBySortIndexAsc();

    List<FAQAdminResponse> findAllByOrderBySortIndexAsc();
}
