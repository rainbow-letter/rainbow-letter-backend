package com.handwoong.rainbowletter.letter.service;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;

import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.handwoong.rainbowletter.common.config.client.ClientConfig;
import com.handwoong.rainbowletter.letter.controller.port.ReplyService;
import com.handwoong.rainbowletter.letter.domain.CreateReply;
import com.handwoong.rainbowletter.letter.domain.Letter;
import com.handwoong.rainbowletter.letter.domain.Reply;
import com.handwoong.rainbowletter.letter.domain.dto.ReplySubmit;
import com.handwoong.rainbowletter.letter.domain.dto.ReplyUpdate;
import com.handwoong.rainbowletter.letter.service.port.LetterRepository;
import com.handwoong.rainbowletter.letter.service.port.ReplyRepository;
import com.handwoong.rainbowletter.mail.domain.MailTemplateType;
import com.handwoong.rainbowletter.mail.domain.dto.MailDto;
import com.handwoong.rainbowletter.mail.service.port.MailService;
import com.handwoong.rainbowletter.notification.controller.port.NotificationService;
import com.handwoong.rainbowletter.notification.domain.Template;
import com.handwoong.rainbowletter.notification.dto.AlimTalkButtonRequest;
import com.handwoong.rainbowletter.notification.dto.AlimTalkRequest;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ReplyServiceImpl implements ReplyService {
    private final NotificationService notificationService;
    private final MailService mailService;
    private final ReplyRepository replyRepository;
    private final LetterRepository letterRepository;
    private final ClientConfig clientConfig;

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
        processReply(reply);
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
    @Transactional
    public Reply update(final ReplyUpdate request, final Long id) {
        final Reply reply = replyRepository.findByIdOrElseThrow(id);
        final Reply updatedReply = reply.update(request);
        return replyRepository.save(updatedReply);
    }

    @Override
    @Async
    @Scheduled(cron = "0 0 10 * * *")
    @Transactional
    public void reservationSubmit() {
        final List<Reply> replies = replyRepository.findAllByReservation();
        replies.forEach(this::processReply);
    }

    private void processReply(final Reply reply) {
        final Reply submittedReply = reply.submit();
        replyRepository.save(submittedReply);

        final Letter letter = letterRepository.findByReplyIdOrElseThrow(reply.id());
        final Letter updatedLetter = letter.updateStatus();
        final Letter savedLetter = letterRepository.save(updatedLetter);
        sendNotificationMail(savedLetter);
        sendNotification(savedLetter);
    }

    private void sendNotificationMail(final Letter letter) {
        final MailDto mailDto = MailDto.builder()
                .email(letter.pet().member().email())
                .subject(letter.pet().name() + "에게 편지가 도착했어요!")
                .url("/share/" + letter.shareLink() + "?utm_source=replycheck")
                .build();
        mailService.send(MailTemplateType.REPLY, mailDto);
    }

    private void sendNotification(final Letter letter) {
        if (Objects.isNull(letter.pet().member().phoneNumber())) {
            return;
        }
        final AlimTalkRequest request = AlimTalkRequest.builder()
                .receiver(letter.pet().member().phoneNumber())
                .templateCode(Template.REPLY.getCode())
                .subject(Template.REPLY.subject(letter.pet().name()))
                .failSubject(Template.REPLY.failSubject(letter.pet().name()))
                .content(
                        Template.REPLY.content(
                                letter.pet().name(),
                                letter.pet().owner(),
                                letter.createdAt() != null ? letter.createdAt()
                                        .format(DateTimeFormatter.ofPattern("yyyy-MM-dd")) : "",
                                letter.pet().name()
                        )
                )
                .failContent(
                        Template.REPLY.failContent(
                                letter.createdAt() != null ? letter.createdAt()
                                        .format(DateTimeFormatter.ofPattern("yyyy-MM-dd")) : "",
                                letter.pet().name()
                        )
                )
                .buttons(
                        new AlimTalkButtonRequest(
                                Template.REPLY.buttons(
                                        clientConfig.getClientUrl().get(0) + "/share/" + letter.shareLink()
                                                + "?utm_source=replycheck")
                        )
                )
                .useEmTitle(false)
                .build();
        notificationService.send(request);
    }
}
