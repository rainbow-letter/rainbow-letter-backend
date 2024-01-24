package com.handwoong.rainbowletter.letter.service.port;

import com.handwoong.rainbowletter.letter.domain.Reply;
import java.util.List;

public interface ReplyRepository {
    Reply save(Reply reply);

    Reply findByIdOrElseThrow(Long id);

    List<Reply> findAllByReservation();
}
