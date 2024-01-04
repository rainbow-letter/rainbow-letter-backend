package com.handwoong.rainbowletter.gpt.infrastructure;

import com.handwoong.rainbowletter.gpt.service.port.ChatGptTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ChatGptTokenRepositoryImpl implements ChatGptTokenRepository {
    private final ChatGptTokenJpaRepository chatGptTokenJpaRepository;
}
