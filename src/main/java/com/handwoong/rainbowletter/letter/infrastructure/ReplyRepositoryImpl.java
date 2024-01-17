package com.handwoong.rainbowletter.letter.infrastructure;

import static com.handwoong.rainbowletter.letter.infrastructure.QLetterEntity.letterEntity;
import static com.handwoong.rainbowletter.letter.infrastructure.QReplyEntity.replyEntity;
import static com.handwoong.rainbowletter.letter.infrastructure.chatgpt.QChatGptEntity.chatGptEntity;

import com.handwoong.rainbowletter.letter.domain.Reply;
import com.handwoong.rainbowletter.letter.domain.ReplyType;
import com.handwoong.rainbowletter.letter.exception.ReplyResourceNotFoundException;
import com.handwoong.rainbowletter.letter.service.port.ReplyRepository;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ReplyRepositoryImpl implements ReplyRepository {
    private final JPAQueryFactory queryFactory;
    private final ReplyJpaRepository replyJpaRepository;

    @Override
    public Reply save(final Reply reply) {
        return replyJpaRepository.save(ReplyEntity.from(reply)).toModel();
    }

    @Override
    public Reply findByIdOrElseThrow(final Long id) {
        return Optional.ofNullable(
                        queryFactory.selectFrom(replyEntity)
                                .distinct()
                                .innerJoin(replyEntity.chatGptEntity, chatGptEntity)
                                .fetchJoin()
                                .innerJoin(replyEntity.letterEntity, letterEntity)
                                .fetchJoin()
                                .where(replyEntity.id.eq(id))
                                .fetchOne()
                )
                .orElseThrow(() -> new ReplyResourceNotFoundException(id))
                .toModel();
    }

    @Override
    public List<Reply> findAllByReservation() {
        return queryFactory.selectFrom(replyEntity)
                .distinct()
                .innerJoin(replyEntity.chatGptEntity, chatGptEntity)
                .fetchJoin()
                .innerJoin(replyEntity.letterEntity, letterEntity)
                .fetchJoin()
                .where(replyEntity.inspection.isTrue().and(replyEntity.type.eq(ReplyType.CHAT_GPT)))
                .fetch()
                .stream()
                .map(ReplyEntity::toModel)
                .toList();
    }
}
