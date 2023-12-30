package com.handwoong.rainbowletter.member.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.handwoong.rainbowletter.common.util.jwt.GrantType;
import com.handwoong.rainbowletter.common.util.jwt.TokenResponse;
import com.handwoong.rainbowletter.mail.domain.dto.MailDto;
import com.handwoong.rainbowletter.member.domain.Email;
import com.handwoong.rainbowletter.member.domain.Member;
import com.handwoong.rainbowletter.member.domain.MemberRole;
import com.handwoong.rainbowletter.member.domain.MemberStatus;
import com.handwoong.rainbowletter.member.domain.OAuthProvider;
import com.handwoong.rainbowletter.member.domain.Password;
import com.handwoong.rainbowletter.member.domain.PhoneNumber;
import com.handwoong.rainbowletter.member.domain.dto.ChangePassword;
import com.handwoong.rainbowletter.member.domain.dto.FindPassword;
import com.handwoong.rainbowletter.member.domain.dto.MemberLogin;
import com.handwoong.rainbowletter.member.domain.dto.MemberRegister;
import com.handwoong.rainbowletter.member.domain.dto.PhoneNumberUpdate;
import com.handwoong.rainbowletter.member.domain.dto.ResetPassword;
import com.handwoong.rainbowletter.member.exception.DuplicateEmailException;
import com.handwoong.rainbowletter.member.exception.MemberEmailNotFoundException;
import com.handwoong.rainbowletter.member.exception.PasswordNotMatchedException;
import com.handwoong.rainbowletter.mock.TestContainer;
import org.junit.jupiter.api.Test;
import org.springframework.security.authentication.BadCredentialsException;

class MemberServiceTest {
    @Test
    void 이메일로_회원을_찾는다() {
        // given
        final Email email = new Email("handwoong@gmail.com");
        final Password password = new Password("@password1");
        final Member member = Member.builder()
                .email(email)
                .password(password)
                .phoneNumber(null)
                .role(MemberRole.ROLE_USER)
                .status(MemberStatus.ACTIVE)
                .provider(OAuthProvider.NONE)
                .providerId(OAuthProvider.NONE.name())
                .build();

        final TestContainer testContainer = new TestContainer();
        testContainer.memberRepository.save(member);

        // when
        final Member findMember = testContainer.memberService.findByEmailOrElseThrow(email);

        // then
        assertThat(findMember.id()).isEqualTo(1);
        assertThat(findMember.email()).hasToString("handwoong@gmail.com");
        assertThat(findMember.password()).hasToString("@password1");
        assertThat(findMember.phoneNumber()).isNull();
        assertThat(findMember.role()).isEqualTo(MemberRole.ROLE_USER);
        assertThat(findMember.status()).isEqualTo(MemberStatus.ACTIVE);
        assertThat(findMember.provider()).isEqualTo(OAuthProvider.NONE);
        assertThat(findMember.providerId()).isEqualTo(OAuthProvider.NONE.name());
    }

    @Test
    void 이메일로_회원을_찾지못하면_예외가_발생한다() {
        // given
        final TestContainer testContainer = new TestContainer();
        final Email email = new Email("handwoong@gmail.com");

        // when
        // then
        assertThatThrownBy(() -> testContainer.memberService.findByEmailOrElseThrow(email))
                .isInstanceOf(MemberEmailNotFoundException.class)
                .hasMessage("해당 회원을 찾을 수 없습니다.");
    }

    @Test
    void 이메일로_회원의_정보를_조회한다() {
        // given
        final Email email = new Email("handwoong@gmail.com");
        final Password password = new Password("@password1");
        final Member member = Member.builder()
                .email(email)
                .password(password)
                .phoneNumber(null)
                .role(MemberRole.ROLE_USER)
                .status(MemberStatus.ACTIVE)
                .provider(OAuthProvider.NONE)
                .providerId(OAuthProvider.NONE.name())
                .build();

        final TestContainer testContainer = new TestContainer();
        testContainer.memberRepository.save(member);

        // when
        final Member infoMember = testContainer.memberService.info(email);

        // then
        assertThat(infoMember.id()).isEqualTo(1);
        assertThat(infoMember.email()).hasToString("handwoong@gmail.com");
        assertThat(infoMember.password()).hasToString("@password1");
        assertThat(infoMember.phoneNumber()).isNull();
        assertThat(infoMember.role()).isEqualTo(MemberRole.ROLE_USER);
        assertThat(infoMember.status()).isEqualTo(MemberStatus.ACTIVE);
        assertThat(infoMember.provider()).isEqualTo(OAuthProvider.NONE);
        assertThat(infoMember.providerId()).isEqualTo(OAuthProvider.NONE.name());
    }

    @Test
    void 이메일로_회원의_정보를_조회하지_못하면_예외가_발생한다() {
        // given
        final TestContainer testContainer = new TestContainer();
        final Email email = new Email("handwoong@gmail.com");

        // when
        // then
        assertThatThrownBy(() -> testContainer.memberService.info(email))
                .isInstanceOf(MemberEmailNotFoundException.class)
                .hasMessage("해당 회원을 찾을 수 없습니다.");
    }

    @Test
    void 회원을_생성한다() {
        // given
        final TestContainer testContainer = new TestContainer();
        final MemberRegister request = MemberRegister.builder()
                .email(new Email("handwoong@gmail.com"))
                .password(new Password("@password1"))
                .build();

        // when
        final Member member = testContainer.memberService.register(request);

        // then
        assertThat(member.id()).isEqualTo(1);
        assertThat(member.email()).hasToString("handwoong@gmail.com");
        assertThat(member.password()).hasToString("@password1" + "$$$!!@@@");
        assertThat(member.phoneNumber()).isNull();
        assertThat(member.role()).isEqualTo(MemberRole.ROLE_USER);
        assertThat(member.status()).isEqualTo(MemberStatus.ACTIVE);
        assertThat(member.provider()).isEqualTo(OAuthProvider.NONE);
        assertThat(member.providerId()).isEqualTo(OAuthProvider.NONE.name());
    }

    @Test
    void 회원_생성시__중복_이메일이_존재하면_예외가_발생한다() {
        // given
        final Email email = new Email("handwoong@gmail.com");
        final Password password = new Password("@password1");
        final Member member = Member.builder()
                .email(email)
                .password(password)
                .phoneNumber(null)
                .role(MemberRole.ROLE_USER)
                .status(MemberStatus.ACTIVE)
                .provider(OAuthProvider.NONE)
                .providerId(OAuthProvider.NONE.name())
                .build();

        final TestContainer testContainer = new TestContainer();
        testContainer.memberRepository.save(member);

        final MemberRegister request = MemberRegister.builder()
                .email(email)
                .password(password)
                .build();

        // when
        // then
        assertThatThrownBy(() -> testContainer.memberService.register(request))
                .isInstanceOf(DuplicateEmailException.class)
                .hasMessage("이미 존재하는 이메일입니다.");
    }

    @Test
    void 가입된_이메일이_없으면_FALSE를_반환한다() {
        // given
        final TestContainer testContainer = new TestContainer();
        final Email email = new Email("handwoong@gmail.com");

        // when
        final boolean result = testContainer.memberService.existsByEmail(email);

        // then
        assertThat(result).isFalse();
    }

    @Test
    void 가입된_이메일이_있으면_TRUE를_반환한다() {
        // given
        final Email email = new Email("handwoong@gmail.com");
        final Password password = new Password("@password1");
        final Member member = Member.builder()
                .email(email)
                .password(password)
                .phoneNumber(null)
                .role(MemberRole.ROLE_USER)
                .status(MemberStatus.ACTIVE)
                .provider(OAuthProvider.NONE)
                .providerId(OAuthProvider.NONE.name())
                .build();

        final TestContainer testContainer = new TestContainer();
        testContainer.memberRepository.save(member);

        // when
        final boolean result = testContainer.memberService.existsByEmail(email);

        // then
        assertThat(result).isTrue();
    }

    @Test
    void 회원_로그인에_성공한다() {
        // given
        final Email email = new Email("handwoong@gmail.com");
        final Password password = new Password("@password1");
        final Member member = Member.builder()
                .email(email)
                .password(password)
                .phoneNumber(null)
                .role(MemberRole.ROLE_USER)
                .status(MemberStatus.ACTIVE)
                .provider(OAuthProvider.NONE)
                .providerId(OAuthProvider.NONE.name())
                .build();

        final TestContainer testContainer = new TestContainer();
        testContainer.memberRepository.save(member);

        final MemberLogin request = MemberLogin.builder()
                .email(email)
                .password(password)
                .build();

        // when
        final TokenResponse result = testContainer.memberService.login(request);

        // then
        assertThat(result.grantType()).isEqualTo(GrantType.BEARER.getName());
        assertThat(result.token()).isEqualTo("accessToken");
    }

    @Test
    void 잘못된_이메일로_로그인하면_예외가_발생한다() {
        // given
        final Email email = new Email("handwoong@gmail.com");
        final Password password = new Password("@password1");
        final Member member = Member.builder()
                .email(email)
                .password(password)
                .phoneNumber(null)
                .role(MemberRole.ROLE_USER)
                .status(MemberStatus.ACTIVE)
                .provider(OAuthProvider.NONE)
                .providerId(OAuthProvider.NONE.name())
                .build();

        final TestContainer testContainer = new TestContainer();
        testContainer.memberRepository.save(member);

        final MemberLogin request = MemberLogin.builder()
                .email(new Email("handwoong@naver.com"))
                .password(password)
                .build();

        // when
        // then
        assertThatThrownBy(() -> testContainer.memberService.login(request))
                .isInstanceOf(BadCredentialsException.class)
                .hasMessage("자격 증명에 실패하였습니다.");
    }

    @Test
    void 잘못된_비밀번호로_로그인하면_예외가_발생한다() {
        // given
        final Email email = new Email("handwoong@gmail.com");
        final Password password = new Password("@password1");
        final Member member = Member.builder()
                .email(email)
                .password(password)
                .phoneNumber(null)
                .role(MemberRole.ROLE_USER)
                .status(MemberStatus.ACTIVE)
                .provider(OAuthProvider.NONE)
                .providerId(OAuthProvider.NONE.name())
                .build();

        final TestContainer testContainer = new TestContainer();
        testContainer.memberRepository.save(member);

        final MemberLogin request = MemberLogin.builder()
                .email(email)
                .password(new Password("pass1234"))
                .build();

        // when
        // then
        assertThatThrownBy(() -> testContainer.memberService.login(request))
                .isInstanceOf(BadCredentialsException.class)
                .hasMessage("자격 증명에 실패하였습니다.");
    }

    @Test
    void 비밀번호_찾기는_이메일을_반환한다() {
        // given
        final Email email = new Email("handwoong@gmail.com");
        final Password password = new Password("@password1");
        final Member member = Member.builder()
                .email(email)
                .password(password)
                .phoneNumber(null)
                .role(MemberRole.ROLE_USER)
                .status(MemberStatus.ACTIVE)
                .provider(OAuthProvider.NONE)
                .providerId(OAuthProvider.NONE.name())
                .build();

        final TestContainer testContainer = new TestContainer();
        testContainer.memberRepository.save(member);

        final FindPassword request = FindPassword.builder()
                .email(email)
                .build();

        // when
        final MailDto result = testContainer.memberService.findPassword(request);

        // then
        assertThat(result.email()).hasToString("handwoong@gmail.com");
    }

    @Test
    void 비밀번호_찾기시_이메일을_찾지못하면_예외가_발생한다() {
        // given
        final Email email = new Email("handwoong@gmail.com");
        final FindPassword request = FindPassword.builder()
                .email(email)
                .build();

        final TestContainer testContainer = new TestContainer();

        // when
        // then
        assertThatThrownBy(() -> testContainer.memberService.findPassword(request))
                .isInstanceOf(MemberEmailNotFoundException.class)
                .hasMessage("해당 회원을 찾을 수 없습니다.");
    }

    @Test
    void 회원의_비밀번호를_변경한다() {
        // given
        final TestContainer testContainer = new TestContainer();

        final Email email = new Email("handwoong@gmail.com");
        final Password password = new Password("@password1");
        final Member member = Member.builder()
                .email(email)
                .password(password.encode(testContainer.passwordEncoder))
                .phoneNumber(null)
                .role(MemberRole.ROLE_USER)
                .status(MemberStatus.ACTIVE)
                .provider(OAuthProvider.NONE)
                .providerId(OAuthProvider.NONE.name())
                .build();

        testContainer.memberRepository.save(member);

        final ChangePassword request = ChangePassword.builder()
                .password(password)
                .newPassword(new Password("pass1234"))
                .build();

        // when
        final Member updateMember = testContainer.memberService.changePassword(email, request);

        // then
        assertThat(updateMember.id()).isEqualTo(1);
        assertThat(updateMember.email()).hasToString("handwoong@gmail.com");
        assertThat(updateMember.password()).hasToString("pass1234" + "$$$!!@@@");
        assertThat(updateMember.phoneNumber()).isNull();
        assertThat(updateMember.role()).isEqualTo(MemberRole.ROLE_USER);
        assertThat(updateMember.status()).isEqualTo(MemberStatus.ACTIVE);
        assertThat(updateMember.provider()).isEqualTo(OAuthProvider.NONE);
        assertThat(updateMember.providerId()).isEqualTo(OAuthProvider.NONE.name());
    }

    @Test
    void 회원_비밀번호_변경시_회원을_찾지_못하면_예외가_발생한다() {
        // given
        final TestContainer testContainer = new TestContainer();

        final Email email = new Email("handwoong@gmail.com");

        final ChangePassword request = ChangePassword.builder().build();

        // when
        // then
        assertThatThrownBy(() -> testContainer.memberService.changePassword(email, request))
                .isInstanceOf(MemberEmailNotFoundException.class)
                .hasMessage("해당 회원을 찾을 수 없습니다.");
    }

    @Test
    void 회원_비밀번호_변경시_기존_비밀번호가_일치하지_않으면_예외가_발생한다() {
        // given
        final TestContainer testContainer = new TestContainer();

        final Email email = new Email("handwoong@gmail.com");
        final Password password = new Password("@password1");
        final Member member = Member.builder()
                .email(email)
                .password(password.encode(testContainer.passwordEncoder))
                .phoneNumber(null)
                .role(MemberRole.ROLE_USER)
                .status(MemberStatus.ACTIVE)
                .provider(OAuthProvider.NONE)
                .providerId(OAuthProvider.NONE.name())
                .build();

        testContainer.memberRepository.save(member);

        final ChangePassword request = ChangePassword.builder()
                .password(new Password("pass1234"))
                .newPassword(new Password("1234pass"))
                .build();

        // when
        // then
        assertThatThrownBy(() -> testContainer.memberService.changePassword(email, request))
                .isInstanceOf(PasswordNotMatchedException.class)
                .hasMessage("비밀번호가 일치하지 않습니다.");
    }

    @Test
    void 회원_비밀번호를_초기화한다() {
        // given
        final TestContainer testContainer = new TestContainer();

        final Email email = new Email("handwoong@gmail.com");
        final Password password = new Password("@password1");
        final Member member = Member.builder()
                .email(email)
                .password(password.encode(testContainer.passwordEncoder))
                .phoneNumber(null)
                .role(MemberRole.ROLE_USER)
                .status(MemberStatus.ACTIVE)
                .provider(OAuthProvider.NONE)
                .providerId(OAuthProvider.NONE.name())
                .build();

        testContainer.memberRepository.save(member);

        final ResetPassword request = ResetPassword.builder()
                .newPassword(new Password("pass1234"))
                .build();

        // when
        final Member updateMember = testContainer.memberService.resetPassword(email, request);

        // then
        assertThat(updateMember.id()).isEqualTo(1);
        assertThat(updateMember.email()).hasToString("handwoong@gmail.com");
        assertThat(updateMember.password()).hasToString("pass1234" + "$$$!!@@@");
        assertThat(updateMember.phoneNumber()).isNull();
        assertThat(updateMember.role()).isEqualTo(MemberRole.ROLE_USER);
        assertThat(updateMember.status()).isEqualTo(MemberStatus.ACTIVE);
        assertThat(updateMember.provider()).isEqualTo(OAuthProvider.NONE);
        assertThat(updateMember.providerId()).isEqualTo(OAuthProvider.NONE.name());
    }

    @Test
    void 회원_비밀번호_초기화시_회원을_찾지_못하면_예외가_발생한다() {
        // given
        final TestContainer testContainer = new TestContainer();

        final Email email = new Email("handwoong@gmail.com");

        final ResetPassword request = ResetPassword.builder().build();

        // when
        // then
        assertThatThrownBy(() -> testContainer.memberService.resetPassword(email, request))
                .isInstanceOf(MemberEmailNotFoundException.class)
                .hasMessage("해당 회원을 찾을 수 없습니다.");
    }

    @Test
    void 회원_휴대폰_번호를_변경한다() {
        // given
        final TestContainer testContainer = new TestContainer();

        final Email email = new Email("handwoong@gmail.com");
        final Password password = new Password("@password1");
        final Member member = Member.builder()
                .email(email)
                .password(password.encode(testContainer.passwordEncoder))
                .phoneNumber(null)
                .role(MemberRole.ROLE_USER)
                .status(MemberStatus.ACTIVE)
                .provider(OAuthProvider.NONE)
                .providerId(OAuthProvider.NONE.name())
                .build();

        testContainer.memberRepository.save(member);

        final PhoneNumberUpdate request = PhoneNumberUpdate.builder()
                .phoneNumber(new PhoneNumber("01011112222"))
                .build();

        // when
        final Member updateMember = testContainer.memberService.updatePhoneNumber(email, request);

        // then
        assertThat(updateMember.id()).isEqualTo(1);
        assertThat(updateMember.email()).hasToString("handwoong@gmail.com");
        assertThat(updateMember.password()).hasToString("@password1" + "$$$!!@@@");
        assertThat(updateMember.phoneNumber()).hasToString("01011112222");
        assertThat(updateMember.role()).isEqualTo(MemberRole.ROLE_USER);
        assertThat(updateMember.status()).isEqualTo(MemberStatus.ACTIVE);
        assertThat(updateMember.provider()).isEqualTo(OAuthProvider.NONE);
        assertThat(updateMember.providerId()).isEqualTo(OAuthProvider.NONE.name());
    }

    @Test
    void 회원_휴대폰_번호_변경시_회원을_찾지_못하면_예외가_발생한다() {
        // given
        final TestContainer testContainer = new TestContainer();

        final Email email = new Email("handwoong@gmail.com");

        final PhoneNumberUpdate request = PhoneNumberUpdate.builder().build();

        // when
        // then
        assertThatThrownBy(() -> testContainer.memberService.updatePhoneNumber(email, request))
                .isInstanceOf(MemberEmailNotFoundException.class)
                .hasMessage("해당 회원을 찾을 수 없습니다.");
    }

    @Test
    void 회원_휴대폰_번호를_삭제한다() {
        // given
        final TestContainer testContainer = new TestContainer();

        final Email email = new Email("handwoong@gmail.com");
        final Password password = new Password("@password1");
        final Member member = Member.builder()
                .email(email)
                .password(password.encode(testContainer.passwordEncoder))
                .phoneNumber(new PhoneNumber("01011112222"))
                .role(MemberRole.ROLE_USER)
                .status(MemberStatus.ACTIVE)
                .provider(OAuthProvider.NONE)
                .providerId(OAuthProvider.NONE.name())
                .build();

        testContainer.memberRepository.save(member);

        // when
        final Member updateMember = testContainer.memberService.deletePhoneNumber(email);

        // then
        assertThat(updateMember.id()).isEqualTo(1);
        assertThat(updateMember.email()).hasToString("handwoong@gmail.com");
        assertThat(updateMember.password()).hasToString("@password1" + "$$$!!@@@");
        assertThat(updateMember.phoneNumber()).isNull();
        assertThat(updateMember.role()).isEqualTo(MemberRole.ROLE_USER);
        assertThat(updateMember.status()).isEqualTo(MemberStatus.ACTIVE);
        assertThat(updateMember.provider()).isEqualTo(OAuthProvider.NONE);
        assertThat(updateMember.providerId()).isEqualTo(OAuthProvider.NONE.name());
    }

    @Test
    void 회원_휴대폰_번호_삭제시_회원을_찾지_못하면_예외가_발생한다() {
        // given
        final TestContainer testContainer = new TestContainer();

        final Email email = new Email("handwoong@gmail.com");

        // when
        // then
        assertThatThrownBy(() -> testContainer.memberService.deletePhoneNumber(email))
                .isInstanceOf(MemberEmailNotFoundException.class)
                .hasMessage("해당 회원을 찾을 수 없습니다.");
    }

    @Test
    void 회원을_탈퇴_처리한다() {
        // given
        final TestContainer testContainer = new TestContainer();

        final Email email = new Email("handwoong@gmail.com");
        final Password password = new Password("@password1");
        final Member member = Member.builder()
                .email(email)
                .password(password.encode(testContainer.passwordEncoder))
                .phoneNumber(null)
                .role(MemberRole.ROLE_USER)
                .status(MemberStatus.ACTIVE)
                .provider(OAuthProvider.NONE)
                .providerId(OAuthProvider.NONE.name())
                .build();

        testContainer.memberRepository.save(member);

        // when
        final Member updateMember = testContainer.memberService.delete(email);

        // then
        assertThat(updateMember.id()).isEqualTo(1);
        assertThat(updateMember.email()).hasToString("handwoong@gmail.com");
        assertThat(updateMember.password()).hasToString("@password1" + "$$$!!@@@");
        assertThat(updateMember.phoneNumber()).isNull();
        assertThat(updateMember.role()).isEqualTo(MemberRole.ROLE_USER);
        assertThat(updateMember.status()).isEqualTo(MemberStatus.LEAVE);
        assertThat(updateMember.provider()).isEqualTo(OAuthProvider.NONE);
        assertThat(updateMember.providerId()).isEqualTo(OAuthProvider.NONE.name());
    }

    @Test
    void 회원_탈퇴시_회원을_찾지_못하면_예외가_발생한다() {
        // given
        final TestContainer testContainer = new TestContainer();

        final Email email = new Email("handwoong@gmail.com");

        // when
        // then
        assertThatThrownBy(() -> testContainer.memberService.delete(email))
                .isInstanceOf(MemberEmailNotFoundException.class)
                .hasMessage("해당 회원을 찾을 수 없습니다.");
    }
}
