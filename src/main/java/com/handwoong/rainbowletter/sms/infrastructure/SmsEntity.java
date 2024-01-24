package com.handwoong.rainbowletter.sms.infrastructure;

import com.handwoong.rainbowletter.common.infrastructure.BaseEntity;
import com.handwoong.rainbowletter.member.domain.PhoneNumber;
import com.handwoong.rainbowletter.sms.domain.Sms;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import java.util.Objects;
import lombok.Getter;
import org.hibernate.Hibernate;

@Getter
@Entity
@Table(name = "sms")
public class SmsEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private int code;

    @NotNull
    private String statusMessage;

    @NotNull
    @Column(length = 20)
    private String sender;

    @NotNull
    @Column(length = 20)
    private String receiver;

    @NotNull
    private String content;

    public static SmsEntity from(final Sms sms) {
        final SmsEntity smsEntity = new SmsEntity();
        smsEntity.id = sms.id();
        smsEntity.code = sms.code();
        smsEntity.statusMessage = sms.statusMessage();
        smsEntity.sender = sms.sender().phoneNumber();
        smsEntity.receiver = sms.receiver().phoneNumber();
        smsEntity.content = sms.content();
        return smsEntity;
    }

    public Sms toModel() {
        return Sms.builder()
                .id(id)
                .code(code)
                .statusMessage(statusMessage)
                .sender(new PhoneNumber(sender))
                .receiver(new PhoneNumber(receiver))
                .content(content)
                .build();
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (Objects.isNull(obj) || Hibernate.getClass(this) != Hibernate.getClass(obj)) {
            return false;
        }
        SmsEntity entity = (SmsEntity) obj;
        return Objects.nonNull(id) && Objects.equals(id, entity.id);
    }

    @Override
    public int hashCode() {
        return Objects.nonNull(id) ? id.intValue() : 0;
    }
}
