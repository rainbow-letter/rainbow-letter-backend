package com.handwoong.rainbowletter.mock.letter;

import com.handwoong.rainbowletter.letter.domain.Reply;
import com.handwoong.rainbowletter.letter.exception.ReplyResourceNotFoundException;
import com.handwoong.rainbowletter.letter.service.port.ReplyRepository;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

public class FakeReplyRepository implements ReplyRepository {
    private final Map<Long, Reply> database = new HashMap<>();

    private Long sequence = 1L;

    @Override
    public Reply save(final Reply reply) {
        Long id = Objects.nonNull(reply.id()) ? reply.id() : sequence++;
        final Reply saveReply = createReply(id, reply);
        database.put(id, saveReply);
        return saveReply;
    }

    private Reply createReply(final Long id, final Reply reply) {
        return Reply.builder()
                .id(id)
                .summary(reply.summary())
                .content(reply.content())
                .inspection(reply.inspection())
                .type(reply.type())
                .readStatus(reply.readStatus())
                .timestamp(LocalDate.now().atStartOfDay())
                .chatGpt(reply.chatGpt())
                .build();
    }

    @Override
    public Reply findByIdOrElseThrow(final Long id) {
        return Optional.ofNullable(database.get(id))
                .orElseThrow(() -> new ReplyResourceNotFoundException(id));
    }
}
