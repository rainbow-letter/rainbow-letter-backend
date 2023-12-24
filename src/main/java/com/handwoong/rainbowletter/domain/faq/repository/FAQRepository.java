package com.handwoong.rainbowletter.domain.faq.repository;

import com.handwoong.rainbowletter.domain.faq.dto.FAQAdminDto;
import com.handwoong.rainbowletter.domain.faq.dto.FAQDto;
import com.handwoong.rainbowletter.domain.faq.model.FAQ;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FAQRepository extends JpaRepository<FAQ, Long> {
    List<FAQDto> findAllByVisibilityTrueOrderBySortIndexAsc();

    List<FAQAdminDto> findAllByOrderBySortIndexAsc();
}
