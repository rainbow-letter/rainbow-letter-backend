package com.handwoong.rainbowletter.letter.infrastructure;

import com.handwoong.rainbowletter.letter.domain.Reply;
import com.handwoong.rainbowletter.letter.service.port.ReplyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ReplyRepositoryImpl implements ReplyRepository {
    private final ReplyJpaRepository replyJpaRepository;

    @Override
    public Reply save(final Reply reply) {
        return replyJpaRepository.save(ReplyEntity.from(reply)).toModel();
    }
}
