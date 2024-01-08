package com.handwoong.rainbowletter.gpt.service;

import com.handwoong.rainbowletter.gpt.controller.port.ChatGptService;
import com.handwoong.rainbowletter.gpt.service.port.ChatGptRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ChatGptServiceImpl implements ChatGptService {
    private final ChatGptRepository chatGptRepository;
}
