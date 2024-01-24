package com.handwoong.rainbowletter.letter.service;

import com.handwoong.rainbowletter.letter.domain.Letter;
import com.handwoong.rainbowletter.letter.domain.Reply;
import com.handwoong.rainbowletter.letter.service.port.LetterRepository;
import com.handwoong.rainbowletter.letter.service.port.ReplyGenerator;
import com.handwoong.rainbowletter.letter.service.port.ReplyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
@RequiredArgsConstructor
public class ReplyEventListener {
    private final ReplyGenerator replyGenerator;
    private final ReplyRepository replyRepository;
    private final LetterRepository letterRepository;

    @Async
    @TransactionalEventListener
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void handle(final Letter letter) {
        final boolean isNotFirst = letterRepository.existsByPet(letter.pet().id());
        final Reply reply = replyGenerator.generate(letter, isNotFirst);
        final Reply savedReply = replyRepository.save(reply);

        final Letter updatedLetter = letter.update(savedReply);
        letterRepository.save(updatedLetter);
    }
}
