package com.handwoong.rainbowletter.letter.service;

import com.handwoong.rainbowletter.letter.controller.port.ReplyService;
import com.handwoong.rainbowletter.letter.domain.CreateReply;
import com.handwoong.rainbowletter.letter.domain.Letter;
import com.handwoong.rainbowletter.letter.domain.Reply;
import com.handwoong.rainbowletter.letter.domain.dto.ReplySubmit;
import com.handwoong.rainbowletter.letter.service.port.LetterRepository;
import com.handwoong.rainbowletter.letter.service.port.ReplyRepository;
import com.handwoong.rainbowletter.mail.domain.MailTemplateType;
import com.handwoong.rainbowletter.mail.domain.SendMail;
import com.handwoong.rainbowletter.mail.domain.dto.MailDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ReplyServiceImpl implements ReplyService {
    private final ReplyRepository replyRepository;
    private final LetterRepository letterRepository;

    @Override
    @CreateReply
    @Transactional
    public Letter generate(final Long letterId) {
        return letterRepository.findByIdOrElseThrow(letterId);
    }

    @Override
    @Transactional
    @SendMail(type = MailTemplateType.REPLY)
    public MailDto submit(final ReplySubmit request, final Long id) {
        final Reply reply = replyRepository.findByIdOrElseThrow(id);
        final Reply submittedReply = reply.submit(request);
        replyRepository.save(submittedReply);

        final Letter letter = letterRepository.findByIdOrElseThrow(request.letterId());
        final Letter updatedLetter = letter.updateStatus();
        letterRepository.save(updatedLetter);
        return MailDto.builder()
                .email(letter.pet().member().email())
                .subject(letter.pet().name() + "에게 편지가 도착했어요!")
                .url("/letter-box/" + letter.id())
                .build();
    }

    @Override
    @Transactional
    public Reply read(final Long id) {
        final Reply reply = replyRepository.findByIdOrElseThrow(id);
        final Reply readReply = reply.read();
        return replyRepository.save(readReply);
    }

    @Override
    @Transactional
    public Reply inspect(final Long id) {
        final Reply reply = replyRepository.findByIdOrElseThrow(id);
        final Reply inspectedReply = reply.inspect();
        return replyRepository.save(inspectedReply);
    }
}
