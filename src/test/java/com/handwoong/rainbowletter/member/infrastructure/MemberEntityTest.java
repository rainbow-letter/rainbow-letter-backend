package com.handwoong.rainbowletter.member.infrastructure;

import static org.assertj.core.api.Assertions.assertThat;

import com.handwoong.rainbowletter.member.domain.Email;
import com.handwoong.rainbowletter.member.domain.Member;
import com.handwoong.rainbowletter.member.domain.MemberRole;
import com.handwoong.rainbowletter.member.domain.MemberStatus;
import com.handwoong.rainbowletter.member.domain.OAuthProvider;
import com.handwoong.rainbowletter.member.domain.Password;
import com.handwoong.rainbowletter.member.domain.PhoneNumber;
import org.junit.jupiter.api.Test;

class MemberEntityTest {
    @Test
    void 회원_도메인으로_엔티티를_생성한다() {
        // given
        final Member member = Member.builder()
                .id(1L)
                .email(new Email("handwoong@gmail.com"))
                .password(new Password("@password1"))
                .phoneNumber(null)
                .role(MemberRole.ROLE_USER)
                .status(MemberStatus.ACTIVE)
                .provider(OAuthProvider.NONE)
                .providerId(OAuthProvider.NONE.name())
                .build();

        // when
        final MemberEntity memberEntity = MemberEntity.from(member);

        // then
        assertThat(memberEntity.getId()).isEqualTo(1);
        assertThat(memberEntity.getEmail()).isEqualTo("handwoong@gmail.com");
        assertThat(memberEntity.getPassword()).isEqualTo("@password1");
        assertThat(memberEntity.getPhoneNumber()).isNull();
        assertThat(memberEntity.getRole()).isEqualTo(MemberRole.ROLE_USER);
        assertThat(memberEntity.getStatus()).isEqualTo(MemberStatus.ACTIVE);
        assertThat(memberEntity.getProvider()).isEqualTo(OAuthProvider.NONE);
        assertThat(memberEntity.getProviderId()).isEqualTo(OAuthProvider.NONE.name());
    }

    @Test
    void 엔티티_생성시_휴대폰_번호가_NULL이_아니다() {
        // given
        final Member member = Member.builder()
                .id(1L)
                .email(new Email("handwoong@gmail.com"))
                .password(new Password("@password1"))
                .phoneNumber(new PhoneNumber("01011112222"))
                .role(MemberRole.ROLE_USER)
                .status(MemberStatus.ACTIVE)
                .provider(OAuthProvider.NONE)
                .providerId(OAuthProvider.NONE.name())
                .build();

        // when
        final MemberEntity memberEntity = MemberEntity.from(member);

        // then
        assertThat(memberEntity.getId()).isEqualTo(1);
        assertThat(memberEntity.getEmail()).isEqualTo("handwoong@gmail.com");
        assertThat(memberEntity.getPassword()).isEqualTo("@password1");
        assertThat(memberEntity.getPhoneNumber()).isEqualTo("01011112222");
        assertThat(memberEntity.getRole()).isEqualTo(MemberRole.ROLE_USER);
        assertThat(memberEntity.getStatus()).isEqualTo(MemberStatus.ACTIVE);
        assertThat(memberEntity.getProvider()).isEqualTo(OAuthProvider.NONE);
        assertThat(memberEntity.getProviderId()).isEqualTo(OAuthProvider.NONE.name());
    }

    @Test
    void 엔티티로_회원_도메인을_생성한다() {
        // given
        final Member member = Member.builder()
                .id(1L)
                .email(new Email("handwoong@gmail.com"))
                .password(new Password("@password1"))
                .phoneNumber(null)
                .role(MemberRole.ROLE_USER)
                .status(MemberStatus.ACTIVE)
                .provider(OAuthProvider.NONE)
                .providerId(OAuthProvider.NONE.name())
                .build();
        final MemberEntity memberEntity = MemberEntity.from(member);

        // when
        final Member convertMember = memberEntity.toModel();

        // then
        assertThat(convertMember.id()).isEqualTo(1);
        assertThat(convertMember.email()).hasToString("handwoong@gmail.com");
        assertThat(convertMember.password()).hasToString("@password1");
        assertThat(convertMember.phoneNumber()).isNull();
        assertThat(convertMember.role()).isEqualTo(MemberRole.ROLE_USER);
        assertThat(convertMember.status()).isEqualTo(MemberStatus.ACTIVE);
        assertThat(convertMember.provider()).isEqualTo(OAuthProvider.NONE);
        assertThat(convertMember.providerId()).isEqualTo(OAuthProvider.NONE.name());
    }

    @Test
    void 엔티티로_회원_도메인_생성시_휴대폰_번호가_NULL이_아니다() {
        // given
        final Member member = Member.builder()
                .id(1L)
                .email(new Email("handwoong@gmail.com"))
                .password(new Password("@password1"))
                .phoneNumber(new PhoneNumber("01011112222"))
                .role(MemberRole.ROLE_USER)
                .status(MemberStatus.ACTIVE)
                .provider(OAuthProvider.NONE)
                .providerId(OAuthProvider.NONE.name())
                .build();
        final MemberEntity memberEntity = MemberEntity.from(member);

        // when
        final Member convertMember = memberEntity.toModel();

        // then
        assertThat(convertMember.id()).isEqualTo(1);
        assertThat(convertMember.email()).hasToString("handwoong@gmail.com");
        assertThat(convertMember.password()).hasToString("@password1");
        assertThat(convertMember.phoneNumber()).hasToString("01011112222");
        assertThat(convertMember.role()).isEqualTo(MemberRole.ROLE_USER);
        assertThat(convertMember.status()).isEqualTo(MemberStatus.ACTIVE);
        assertThat(convertMember.provider()).isEqualTo(OAuthProvider.NONE);
        assertThat(convertMember.providerId()).isEqualTo(OAuthProvider.NONE.name());
    }
}