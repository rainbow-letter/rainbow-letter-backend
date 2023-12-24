package com.handwoong.rainbowletter.domain.member.model;

import com.handwoong.rainbowletter.config.security.oauth.OAuthProvider;
import com.handwoong.rainbowletter.domain.BaseEntity;
import com.handwoong.rainbowletter.domain.member.dto.ChangePasswordRequest;
import com.handwoong.rainbowletter.domain.member.dto.ChangePhoneNumberRequest;
import com.handwoong.rainbowletter.domain.member.dto.MemberRegisterRequest;
import com.handwoong.rainbowletter.exception.ErrorCode;
import com.handwoong.rainbowletter.exception.RainbowLetterException;
import jakarta.annotation.Nullable;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import java.util.Objects;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;

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

    @Nullable
    @Column(length = 20, unique = true)
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

    private Member(final String email, final String password, final OAuthProvider provider, final String providerId) {
        this.email = email;
        this.password = password;
        this.role = MemberRole.ROLE_USER;
        this.status = MemberStatus.INACTIVE;
        this.provider = provider;
        this.providerId = providerId;
    }

    public static Member create(final MemberRegisterRequest request) {
        return new Member(request.email(), request.password(), OAuthProvider.NONE, OAuthProvider.NONE.name());
    }

    public static Member create(final MemberRegisterRequest request,
                                final OAuthProvider provider,
                                final String providerId) {
        return new Member(request.email(), request.password(), provider, providerId);
    }

    public void encodePassword(final PasswordEncoder passwordEncoder) {
        password = passwordEncoder.encode(password);
    }

    public boolean isStatusMismatch(final MemberStatus status) {
        return this.status != status;
    }

    public void changeStatus(final MemberStatus status) {
        if (status.equals(MemberStatus.INACTIVE)) {
            throw new RainbowLetterException(ErrorCode.INVALID_MEMBER_STATUS, status.name());
        }
        this.status = status;
    }

    public void changePassword(final ChangePasswordRequest request, final PasswordEncoder passwordEncoder) {
        if (Objects.nonNull(request.password())) {
            comparePassword(request.password(), passwordEncoder);
        }
        password = request.newPassword();
        encodePassword(passwordEncoder);
    }

    private void comparePassword(final String password, final PasswordEncoder passwordEncoder) {
        if (!passwordEncoder.matches(password, this.password)) {
            throw new RainbowLetterException(ErrorCode.INVALID_PASSWORD);
        }
    }

    public void changePhoneNumber(final ChangePhoneNumberRequest request) {
        this.phoneNumber = request.phoneNumber();
    }

    public void updateProvider(final OAuthProvider oAuthProvider, final String providerId) {
        if (this.provider.equals(oAuthProvider)) {
            return;
        }
        this.provider = oAuthProvider;
        this.providerId = providerId;
    }

    public void deletePhoneNumber() {
        this.phoneNumber = null;
    }
}
