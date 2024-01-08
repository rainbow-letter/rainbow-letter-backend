package com.handwoong.rainbowletter.letter.service.port;

import com.handwoong.rainbowletter.letter.domain.dto.chatgpt.ChatGptRequest;
import com.handwoong.rainbowletter.letter.domain.dto.chatgpt.ChatGptResponse;

public interface ChatGptExecutor {
    ChatGptResponse execute(ChatGptRequest request);
}
