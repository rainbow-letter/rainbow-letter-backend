package com.handwoong.rainbowletter.domain.letter.service;

import com.handwoong.rainbowletter.domain.letter.dto.ReplyRequestDto;

public interface LetterService {
    void reply(final ReplyRequestDto replyRequest);
}
