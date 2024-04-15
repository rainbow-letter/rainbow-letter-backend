package com.handwoong.rainbowletter.letter.service;

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
import com.handwoong.rainbowletter.sms.domain.dto.SmsSend;
import com.handwoong.rainbowletter.sms.service.port.SmsService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ReplyServiceImpl implements ReplyService {
    private static final String SMS_CONTENT = """
                 %s로부터 편지가 도착했어요!
                \s
                 답장 보러 가기
                 %s
                \s
                 무지개마을 커스텀 케이스&그립톡 사전예약 중이에요!
                 아이의 사진으로 특별한 케이스를 만들어보세요
                 https://forms.gle/FdESx63GDxgGrSPn8
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

    private void processReply(final Reply reply) {
        final Reply submittedReply = reply.submit();
        replyRepository.save(submittedReply);

        final Letter letter = letterRepository.findByReplyIdOrElseThrow(reply.id());
        final Letter updatedLetter = letter.updateStatus();
        final Letter savedLetter = letterRepository.save(updatedLetter);
        sendNotificationMail(savedLetter);
        sendNotificationSms(savedLetter);
    }

    private void sendNotificationMail(final Letter letter) {
        final MailDto mailDto = MailDto.builder()
                .email(letter.pet().member().email())
                .subject(letter.pet().name() + "에게 편지가 도착했어요!")
                .url("/share/" + letter.shareLink() + "?utm_source=replycheck")
                .build();
        mailService.send(MailTemplateType.REPLY, mailDto);
    }

    private void sendNotificationSms(final Letter letter) {
        if (Objects.isNull(letter.pet().member().phoneNumber())) {
            return;
        }
        final SmsSend request = SmsSend.builder()
                .receiver(letter.pet().member().phoneNumber())
                .content(String.format(
                        SMS_CONTENT, letter.pet().name(),
                        clientConfig.getClientUrl().get(0) + "/share/" + letter.shareLink() + "?utm_source=replycheck"
                ))
                .build();
        smsService.send(request);
    }
}
