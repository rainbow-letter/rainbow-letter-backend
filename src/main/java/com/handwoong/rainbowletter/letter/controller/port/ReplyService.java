package com.handwoong.rainbowletter.letter.controller.port;

import com.handwoong.rainbowletter.letter.domain.Letter;
import com.handwoong.rainbowletter.letter.domain.Reply;
import com.handwoong.rainbowletter.letter.domain.dto.ReplySubmit;
import com.handwoong.rainbowletter.letter.domain.dto.ReplyUpdate;

public interface ReplyService {
    Letter generate(Long letterId);

    void submit(ReplySubmit request, Long id);

    Reply read(Long id);

    Reply inspect(Long id);

    Reply update(ReplyUpdate request, Long id);

    void reservationSubmit();
}
