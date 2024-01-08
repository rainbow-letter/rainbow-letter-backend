package com.handwoong.rainbowletter.letter.controller.port;

import com.handwoong.rainbowletter.letter.domain.Letter;
import com.handwoong.rainbowletter.letter.domain.Reply;
import com.handwoong.rainbowletter.letter.domain.dto.ReplySubmit;

public interface ReplyService {
    Letter generate(Long letterId);

    Reply submit(ReplySubmit request, Long id);

    Reply read(Long id);
}
