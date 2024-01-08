package com.handwoong.rainbowletter.gpt.infrastructure;

import com.handwoong.rainbowletter.gpt.service.port.ChatGptRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ChatGptRepositoryImpl implements ChatGptRepository {
    private final ChatGptJpaRepository chatGptJpaRepository;
}
