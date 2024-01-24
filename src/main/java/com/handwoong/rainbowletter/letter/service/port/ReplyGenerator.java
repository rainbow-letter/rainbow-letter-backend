package com.handwoong.rainbowletter.letter.service.port;

import com.handwoong.rainbowletter.letter.domain.Letter;
import com.handwoong.rainbowletter.letter.domain.Reply;

public interface ReplyGenerator {
    Reply generate(Letter letter, boolean isNotFirst);
}
