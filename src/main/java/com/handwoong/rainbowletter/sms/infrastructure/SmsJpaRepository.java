package com.handwoong.rainbowletter.sms.infrastructure;

import org.springframework.data.jpa.repository.JpaRepository;

public interface SmsJpaRepository extends JpaRepository<SmsEntity, Long> {
}
