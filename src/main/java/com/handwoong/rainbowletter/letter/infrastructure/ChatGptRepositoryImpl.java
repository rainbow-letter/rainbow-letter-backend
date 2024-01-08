package com.handwoong.rainbowletter.letter.infrastructure;

import com.handwoong.rainbowletter.letter.service.port.ChatGptRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ChatGptRepositoryImpl implements ChatGptRepository {
    private final ChatGptJpaRepository chatGptJpaRepository;
}
