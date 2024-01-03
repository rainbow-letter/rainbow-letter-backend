package com.handwoong.rainbowletter.faq.infrastructure;

import org.springframework.data.jpa.repository.JpaRepository;

public interface FAQJpaRepository extends JpaRepository<FAQEntity, Long> {
}
