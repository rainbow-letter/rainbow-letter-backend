package com.handwoong.rainbowletter.faq.infrastructure;

import com.handwoong.rainbowletter.faq.domain.dto.FAQAdminDto;
import com.handwoong.rainbowletter.faq.domain.dto.FAQDto;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FAQRepository extends JpaRepository<FAQ, Long> {
    List<FAQDto> findAllByVisibilityTrueOrderBySortIndexAsc();

    List<FAQAdminDto> findAllByOrderBySortIndexAsc();
}
