package com.handwoong.rainbowletter.letter.infrastructure.chatgpt;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatGptJpaRepository extends JpaRepository<ChatGptEntity, Long> {
}
