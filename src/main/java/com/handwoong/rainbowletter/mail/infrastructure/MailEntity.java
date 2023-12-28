package com.handwoong.rainbowletter.mail.infrastructure;

import com.handwoong.rainbowletter.common.infrastructure.BaseEntity;
import com.handwoong.rainbowletter.mail.domain.EmailTemplateType;
import com.handwoong.rainbowletter.mail.domain.Mail;
import com.handwoong.rainbowletter.member.domain.Email;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
@Entity
@Table(name = "mail")
public class MailEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(length = 50, unique = true)
    private String email;

    @NotNull
    @Column(length = 100)
    private String title;

    @NotNull
    @Column(columnDefinition = "TEXT")
    private String content;

    @Enumerated(EnumType.STRING)
    private EmailTemplateType templateType;

    public Mail toModel() {
        return Mail.builder()
                .id(id)
                .email(new Email(email))
                .title(title)
                .content(content)
                .templateType(templateType)
                .build();
    }

    public static MailEntity fromModel(final Mail mail) {
        final MailEntity mailEntity = new MailEntity();
        mailEntity.id = mail.id();
        mailEntity.email = mail.email().toString();
        mailEntity.title = mail.title();
        mailEntity.content = mail.content();
        mailEntity.templateType = mail.templateType();
        return mailEntity;
    }
}
