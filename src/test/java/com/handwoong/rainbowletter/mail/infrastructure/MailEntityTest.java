package com.handwoong.rainbowletter.mail.infrastructure;

import static org.assertj.core.api.Assertions.assertThat;

import com.handwoong.rainbowletter.mail.domain.Mail;
import com.handwoong.rainbowletter.mail.domain.MailTemplateType;
import com.handwoong.rainbowletter.member.domain.Email;
import org.junit.jupiter.api.Test;

class MailEntityTest {
    @Test
    void 메일_도메인으로_엔티티를_생성한다() {
        // given
        final Mail mail = Mail.builder()
                .id(1L)
                .email(new Email("handwoong@gmail.com"))
                .title("비밀번호 찾기 안내입니다.")
                .content("아래 링크로 접속하여 비밀번호를 변경해 주세요.")
                .templateType(MailTemplateType.FIND_PASSWORD)
                .build();

        // when
        final MailEntity mailEntity = MailEntity.fromModel(mail);

        // then
        assertThat(mailEntity.getId()).isEqualTo(1);
        assertThat(mailEntity.getEmail()).hasToString("handwoong@gmail.com");
        assertThat(mailEntity.getTitle()).hasToString("비밀번호 찾기 안내입니다.");
        assertThat(mailEntity.getContent()).isEqualTo("아래 링크로 접속하여 비밀번호를 변경해 주세요.");
        assertThat(mailEntity.getTemplateType()).isEqualTo(MailTemplateType.FIND_PASSWORD);
    }

    @Test
    void 엔티티로_메일_도메인을_생성한다() {
        // given
        final Mail mail = Mail.builder()
                .id(1L)
                .email(new Email("handwoong@gmail.com"))
                .title("비밀번호 찾기 안내입니다.")
                .content("아래 링크로 접속하여 비밀번호를 변경해 주세요.")
                .templateType(MailTemplateType.FIND_PASSWORD)
                .build();
        final MailEntity mailEntity = MailEntity.fromModel(mail);

        // when
        final Mail convertMail = mailEntity.toModel();

        // then
        assertThat(convertMail.id()).isEqualTo(1);
        assertThat(convertMail.email()).hasToString("handwoong@gmail.com");
        assertThat(convertMail.title()).hasToString("비밀번호 찾기 안내입니다.");
        assertThat(convertMail.content()).isEqualTo("아래 링크로 접속하여 비밀번호를 변경해 주세요.");
        assertThat(convertMail.templateType()).isEqualTo(MailTemplateType.FIND_PASSWORD);
    }
}
