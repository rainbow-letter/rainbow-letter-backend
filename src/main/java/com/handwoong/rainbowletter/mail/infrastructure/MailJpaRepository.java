package com.handwoong.rainbowletter.mail.infrastructure;

import org.springframework.data.jpa.repository.JpaRepository;

public interface MailJpaRepository extends JpaRepository<MailEntity, Long> {
}
