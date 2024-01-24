package com.handwoong.rainbowletter.mail.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.handwoong.rainbowletter.mail.domain.dto.MailTemplate;
import com.handwoong.rainbowletter.member.domain.Email;
import org.junit.jupiter.api.Test;

class MailTest {
    @Test
    void 메일을_생성한다() {
        // given
        final Email email = new Email("handwoong@gmail.com");
        final MailTemplate template = new MailTemplate("비밀번호 찾기 안내입니다.", "아래 링크로 접속하여 비밀번호를 변경해 주세요.");

        // when
        final Mail mail = Mail.create(email, template, MailTemplateType.FIND_PASSWORD);

        // then
        assertThat(mail.id()).isNull();
        assertThat(mail.email()).hasToString("handwoong@gmail.com");
        assertThat(mail.title()).hasToString("비밀번호 찾기 안내입니다.");
        assertThat(mail.content()).isEqualTo("아래 링크로 접속하여 비밀번호를 변경해 주세요.");
        assertThat(mail.templateType()).isEqualTo(MailTemplateType.FIND_PASSWORD);
    }
}
