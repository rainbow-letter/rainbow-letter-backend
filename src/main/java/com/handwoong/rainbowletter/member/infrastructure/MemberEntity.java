package com.handwoong.rainbowletter.member.infrastructure;

import com.handwoong.rainbowletter.common.infrastructure.BaseEntity;
import com.handwoong.rainbowletter.member.domain.Email;
import com.handwoong.rainbowletter.member.domain.Member;
import com.handwoong.rainbowletter.member.domain.MemberRole;
import com.handwoong.rainbowletter.member.domain.MemberStatus;
import com.handwoong.rainbowletter.member.domain.OAuthProvider;
import com.handwoong.rainbowletter.member.domain.Password;
import com.handwoong.rainbowletter.member.domain.PhoneNumber;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import java.util.Objects;
import lombok.Getter;

@Getter
@Entity
@Table(name = "member")
public class MemberEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(length = 50, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(length = 20)
    private String phoneNumber;

    @NotNull
    @Enumerated(EnumType.STRING)
    private MemberRole role;

    @NotNull
    @Enumerated(EnumType.STRING)
    private MemberStatus status;

    @NotNull
    @Enumerated(EnumType.STRING)
    private OAuthProvider provider;

    @NotNull
    private String providerId;

    public Member toModel() {
        return Member.builder()
                .id(id)
                .email(new Email(email))
                .password(new Password(password))
                .phoneNumber(Objects.nonNull(phoneNumber) ? new PhoneNumber(phoneNumber) : null)
                .role(role)
                .status(status)
                .provider(provider)
                .providerId(providerId)
                .build();
    }

    public static MemberEntity fromModel(final Member member) {
        final MemberEntity memberEntity = new MemberEntity();
        memberEntity.id = member.id();
        memberEntity.email = member.email().toString();
        memberEntity.password = member.password().toString();
        memberEntity.phoneNumber = Objects.nonNull(member.phoneNumber()) ? member.phoneNumber().toString() : null;
        memberEntity.role = member.role();
        memberEntity.status = member.status();
        memberEntity.provider = member.provider();
        memberEntity.providerId = member.providerId();
        return memberEntity;
    }
}
