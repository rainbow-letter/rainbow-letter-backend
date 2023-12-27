package com.handwoong.rainbowletter.letter.service;

import com.handwoong.rainbowletter.letter.dto.ReplyRequestDto;

public interface LetterService {
    void reply(final ReplyRequestDto replyRequest);
}
