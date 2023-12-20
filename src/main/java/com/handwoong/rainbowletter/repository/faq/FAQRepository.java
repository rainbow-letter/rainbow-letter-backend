package com.handwoong.rainbowletter.repository.faq;

import com.handwoong.rainbowletter.domain.faq.FAQ;
import com.handwoong.rainbowletter.dto.faq.FAQAdminDto;
import com.handwoong.rainbowletter.dto.faq.FAQDto;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FAQRepository extends JpaRepository<FAQ, Long> {
    List<FAQDto> findAllByVisibilityTrueOrderBySortIndexAsc();

    List<FAQAdminDto> findAllByOrderBySortIndexAsc();
}
