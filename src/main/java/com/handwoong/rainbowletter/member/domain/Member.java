package com.handwoong.rainbowletter.member.domain;

import com.handwoong.rainbowletter.member.domain.dto.ChangePassword;
import com.handwoong.rainbowletter.member.domain.dto.MemberRegister;
import com.handwoong.rainbowletter.member.domain.dto.PhoneNumberUpdate;
import com.handwoong.rainbowletter.member.domain.dto.ResetPassword;
import com.handwoong.rainbowletter.member.exception.MemberStatusNotValidException;
import lombok.Builder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Builder
public record Member(
        Long id,
        Email email,
        Password password,
        PhoneNumber phoneNumber,
        MemberRole role,
        MemberStatus status,
        OAuthProvider provider,
        String providerId
) {
    public static Member create(final MemberRegister request, final PasswordEncoder passwordEncoder) {
        return Member.builder()
                .email(request.email())
                .password(request.password().encode(passwordEncoder))
                .role(MemberRole.ROLE_USER)
                .status(MemberStatus.ACTIVE)
                .provider(OAuthProvider.NONE)
                .providerId(OAuthProvider.NONE.name())
                .build();
    }

    public static Member create(final MemberRegister request,
                                final PasswordEncoder passwordEncoder,
                                final OAuthProvider provider,
                                final String providerId) {
        return Member.builder()
                .email(request.email())
                .password(request.password().encode(passwordEncoder))
                .role(MemberRole.ROLE_USER)
                .status(MemberStatus.ACTIVE)
                .provider(provider)
                .providerId(providerId)
                .build();
    }

    public boolean isStatusMismatch(final MemberStatus status) {
        return this.status != status;
    }

    public Member update(final OAuthProvider provider, final String providerId) {
        return Member.builder()
                .id(id)
                .email(email)
                .password(password)
                .phoneNumber(phoneNumber)
                .role(role)
                .status(status)
                .provider(provider)
                .providerId(providerId)
                .build();
    }

    public Member update(final PhoneNumberUpdate request) {
        return Member.builder()
                .id(id)
                .email(email)
                .password(password)
                .phoneNumber(request.phoneNumber())
                .role(role)
                .status(status)
                .provider(provider)
                .providerId(providerId)
                .build();
    }

    public Member update(final MemberStatus status) {
        if (this.status.equals(MemberStatus.INACTIVE)) {
            throw new MemberStatusNotValidException(this.status.name());
        }
        return Member.builder()
                .id(id)
                .email(email)
                .password(password)
                .phoneNumber(phoneNumber)
                .role(role)
                .status(status)
                .provider(provider)
                .providerId(providerId)
                .build();
    }

    public Member changePassword(final ChangePassword request, final PasswordEncoder passwordEncoder) {
        password.compare(passwordEncoder, request.password());
        return Member.builder()
                .id(id)
                .email(email)
                .password(request.newPassword().encode(passwordEncoder))
                .phoneNumber(phoneNumber)
                .role(role)
                .status(status)
                .provider(provider)
                .providerId(providerId)
                .build();
    }

    public Member resetPassword(final ResetPassword request, final PasswordEncoder passwordEncoder) {
        return Member.builder()
                .id(id)
                .email(email)
                .password(request.newPassword().encode(passwordEncoder))
                .phoneNumber(phoneNumber)
                .role(role)
                .status(status)
                .provider(provider)
                .providerId(providerId)
                .build();
    }

    public Member deletePhoneNumber() {
        return Member.builder()
                .id(id)
                .email(email)
                .password(password)
                .role(role)
                .status(status)
                .provider(provider)
                .providerId(providerId)
                .build();
    }
}
