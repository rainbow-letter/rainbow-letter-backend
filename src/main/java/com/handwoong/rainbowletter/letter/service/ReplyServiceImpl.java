package com.handwoong.rainbowletter.letter.service;

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
import com.handwoong.rainbowletter.sms.domain.dto.SmsSend;
import com.handwoong.rainbowletter.sms.service.port.SmsService;
import java.util.List;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ReplyServiceImpl implements ReplyService {
    private static final String SMS_CONTENT = """
                %s에게 편지가 도착했어요!
                
                %s
            """;

    private final SmsService smsService;
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

    private void processReply(Reply reply) {
        final Reply submittedReply = reply.submit();
        final Reply updatedReply = replyRepository.save(submittedReply);

        final Letter letter = letterRepository.findByIdOrElseThrow(reply.letter().id());
        final Letter updatedLetter = letter.updateStatus(updatedReply);
        final Letter savedLetter = letterRepository.save(updatedLetter);
        sendNotificationMail(savedLetter);
        sendNotificationSms(savedLetter);
    }

    private void sendNotificationMail(final Letter letter) {
        final MailDto mailDto = MailDto.builder()
                .email(letter.pet().member().email())
                .subject(letter.pet().name() + "에게 편지가 도착했어요!")
                .url("/letter-box/" + letter.id())
                .build();
        mailService.send(MailTemplateType.REPLY, mailDto);
    }

    private void sendNotificationSms(final Letter letter) {
        if (Objects.isNull(letter.pet().member().phoneNumber())) {
            return;
        }
        final SmsSend request = SmsSend.builder()
                .receiver(letter.pet().member().phoneNumber())
                .content(String.format(SMS_CONTENT, letter.pet().name(),
                        clientConfig.getClientUrl() + "/letter-box/" + letter.id()))
                .build();
        smsService.send(request);
    }
}
