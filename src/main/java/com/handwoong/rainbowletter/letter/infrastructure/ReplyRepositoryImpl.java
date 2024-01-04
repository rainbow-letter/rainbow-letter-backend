package com.handwoong.rainbowletter.letter.infrastructure;

import com.handwoong.rainbowletter.letter.service.port.ReplyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ReplyRepositoryImpl implements ReplyRepository {
    private final ReplyJpaRepository replyJpaRepository;
}
