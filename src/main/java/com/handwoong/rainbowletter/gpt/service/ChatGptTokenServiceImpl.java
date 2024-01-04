package com.handwoong.rainbowletter.gpt.service;

import com.handwoong.rainbowletter.gpt.controller.port.ChatGptTokenService;
import com.handwoong.rainbowletter.gpt.service.port.ChatGptTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ChatGptTokenServiceImpl implements ChatGptTokenService {
    private final ChatGptTokenRepository chatGptTokenRepository;
}
