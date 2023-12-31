package com.handwoong.rainbowletter.mail.service;

import static org.assertj.core.api.Assertions.assertThat;

import com.handwoong.rainbowletter.mail.domain.Mail;
import com.handwoong.rainbowletter.mail.domain.MailTemplateType;
import com.handwoong.rainbowletter.member.domain.Email;
import com.handwoong.rainbowletter.mock.FakeMailTemplateManager;
import com.handwoong.rainbowletter.mock.TestContainer;
import jakarta.mail.MessagingException;
import org.junit.jupiter.api.Test;

class MailServiceImplTest {
    @Test
    void 메일을_발송한다() throws MessagingException {
        // given
        final Email email = new Email("handwoong@gmail.com");

        final TestContainer testContainer = new TestContainer();
        final MailServiceImpl mailService =
                new MailServiceImpl(
                        testContainer.mailSender,
                        new FakeMailTemplateManager("비밀번호 찾기 안내입니다.", "아래 링크로 접속하여 비밀번호를 변경해 주세요."),
                        testContainer.mailRepository
                );

        // when
        final Mail mail = mailService.send(email, MailTemplateType.FIND_PASSWORD);

        // then
        assertThat(mail.id()).isEqualTo(1);
        assertThat(mail.email()).hasToString("handwoong@gmail.com");
        assertThat(mail.title()).hasToString("비밀번호 찾기 안내입니다.");
        assertThat(mail.content()).isEqualTo("아래 링크로 접속하여 비밀번호를 변경해 주세요.");
        assertThat(mail.templateType()).isEqualTo(MailTemplateType.FIND_PASSWORD);
    }
}
