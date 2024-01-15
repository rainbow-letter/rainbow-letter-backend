package com.handwoong.rainbowletter.letter.service;

import com.handwoong.rainbowletter.letter.controller.port.ReplyService;
import com.handwoong.rainbowletter.letter.domain.CreateReply;
import com.handwoong.rainbowletter.letter.domain.Letter;
import com.handwoong.rainbowletter.letter.domain.Reply;
import com.handwoong.rainbowletter.letter.domain.dto.ReplySubmit;
import com.handwoong.rainbowletter.letter.service.port.LetterRepository;
import com.handwoong.rainbowletter.letter.service.port.ReplyRepository;
import com.handwoong.rainbowletter.mail.domain.MailTemplateType;
import com.handwoong.rainbowletter.mail.domain.dto.MailDto;
import com.handwoong.rainbowletter.mail.service.port.MailService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ReplyServiceImpl implements ReplyService {
    private final MailService mailService;
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
    public void submit(final ReplySubmit request, final Long id) {
        final Reply reply = replyRepository.findByIdOrElseThrow(id);
        final Reply submittedReply = reply.submit(request);
        replyRepository.save(submittedReply);

        final Letter letter = letterRepository.findByIdOrElseThrow(request.letterId());
        final Letter updatedLetter = letter.updateStatus();
        letterRepository.save(updatedLetter);
        sendNotificationMail(letter);
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

    @Override
    @Scheduled(cron = "0 0 10 * * *")
    @Transactional
    public void reservationSubmit() {
        final List<Reply> replies = replyRepository.findAllByReservation();
        replies.forEach(this::processReply);
    }

    private void processReply(Reply reply) {
        final ReplySubmit request = ReplySubmit.builder()
                .letterId(reply.letter().id())
                .summary(reply.summary())
                .content(reply.content())
                .build();
        final Reply submittedReply = reply.submit(request);
        replyRepository.save(submittedReply);

        final Letter letter = letterRepository.findByIdOrElseThrow(request.letterId());
        final Letter updatedLetter = letter.updateStatus();
        letterRepository.save(updatedLetter);
        sendNotificationMail(letter);
    }

    private void sendNotificationMail(final Letter letter) {
        final MailDto mailDto = MailDto.builder()
                .email(letter.pet().member().email())
                .subject(letter.pet().name() + "에게 편지가 도착했어요!")
                .url("/letter-box/" + letter.id())
                .build();
        mailService.send(MailTemplateType.REPLY, mailDto);
    }
}
