package com.handwoong.rainbowletter.gpt.infrastructure;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatGptTokenJpaRepository extends JpaRepository<ChatGptTokenEntity, Long> {
}
