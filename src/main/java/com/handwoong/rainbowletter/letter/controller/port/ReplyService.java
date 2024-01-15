package com.handwoong.rainbowletter.letter.controller.port;

import com.handwoong.rainbowletter.letter.domain.Letter;
import com.handwoong.rainbowletter.letter.domain.Reply;
import com.handwoong.rainbowletter.letter.domain.dto.ReplySubmit;
import com.handwoong.rainbowletter.mail.domain.dto.MailDto;

public interface ReplyService {
    Letter generate(Long letterId);

    MailDto submit(ReplySubmit request, Long id);

    Reply read(Long id);

    Reply inspect(Long id);
}
