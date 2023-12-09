package com.handwoong.rainbowletter.service.letter;

import com.handwoong.rainbowletter.dto.letter.ReplyRequestDto;

public interface LetterService {
    void reply(final ReplyRequestDto replyRequest);
}
