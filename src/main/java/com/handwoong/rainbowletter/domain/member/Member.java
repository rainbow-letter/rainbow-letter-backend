package com.handwoong.rainbowletter.domain.member;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;

import org.springframework.security.crypto.password.PasswordEncoder;

import com.handwoong.rainbowletter.domain.BaseEntity;
import com.handwoong.rainbowletter.dto.member.MemberRegisterRequest;
import com.handwoong.rainbowletter.exception.ErrorCode;
import com.handwoong.rainbowletter.exception.RainbowLetterException;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(length = 50, unique = true)
    private String email;

    @NotNull
    private String password;

    @NotNull
    @Enumerated(EnumType.STRING)
    private MemberRole role;

    @NotNull
    @Enumerated(EnumType.STRING)
    private MemberStatus status;

    private Member(final String email, final String password) {
        this.email = email;
        this.password = password;
        this.role = MemberRole.ROLE_USER;
        this.status = MemberStatus.INACTIVE;
    }

    public static Member create(final MemberRegisterRequest request) {
        return new Member(request.email(), request.password());
    }

    public void encodePassword(final PasswordEncoder passwordEncoder) {
        password = passwordEncoder.encode(password);
    }

    public boolean isStatusMismatch(final MemberStatus status) {
        return this.status != status;
    }

    public void changeStatus(final MemberStatus status) {
        if (isStatusMismatch(MemberStatus.INACTIVE)) {
            this.status = status;
        }
        throw new RainbowLetterException(ErrorCode.INVALID_MEMBER_STATUS, status.name());
    }
}
