package com.handwoong.rainbowletter.letter.controller.port;

import com.handwoong.rainbowletter.letter.dto.ReplyRequestDto;

public interface LetterLegacyService {
    void reply(final ReplyRequestDto replyRequest);
}
