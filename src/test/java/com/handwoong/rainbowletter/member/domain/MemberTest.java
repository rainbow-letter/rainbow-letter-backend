package com.handwoong.rainbowletter.member.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.handwoong.rainbowletter.member.domain.dto.ChangePassword;
import com.handwoong.rainbowletter.member.domain.dto.MemberRegister;
import com.handwoong.rainbowletter.member.domain.dto.PhoneNumberUpdate;
import com.handwoong.rainbowletter.member.domain.dto.ResetPassword;
import com.handwoong.rainbowletter.member.exception.MemberStatusNotValidException;
import com.handwoong.rainbowletter.member.exception.PasswordNotMatchedException;
import com.handwoong.rainbowletter.mock.member.MemberTestContainer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.EnumSource.Mode;
import org.junit.jupiter.params.provider.ValueSource;

class MemberTest {
    @Test
    void 일반_회원을_생성한다() {
        // given
        final MemberTestContainer testContainer = new MemberTestContainer();
        final MemberRegister request = MemberRegister.builder()
                .email(new Email("handwoong@gmail.com"))
                .password(new Password("@password1"))
                .build();

        // when
        final Member member = Member.create(request, testContainer.passwordEncoder);

        // then
        assertThat(member.id()).isNull();
        assertThat(member.email()).hasToString("handwoong@gmail.com");
        assertThat(member.password()).hasToString("@password1" + "$$$!!@@@");
        assertThat(member.role()).isEqualTo(MemberRole.ROLE_USER);
        assertThat(member.status()).isEqualTo(MemberStatus.ACTIVE);
        assertThat(member.provider()).isEqualTo(OAuthProvider.NONE);
        assertThat(member.providerId()).isEqualTo(OAuthProvider.NONE.name());
    }

    @Test
    void 소셜_회원을_생성한다() {
        // given
        final MemberTestContainer testContainer = new MemberTestContainer();
        final MemberRegister request = MemberRegister.builder()
                .email(new Email("handwoong@gmail.com"))
                .password(new Password("@password1"))
                .build();

        // when
        final Member member =
                Member.create(request, testContainer.passwordEncoder, OAuthProvider.GOOGLE, "google");

        // then
        assertThat(member.id()).isNull();
        assertThat(member.email()).hasToString("handwoong@gmail.com");
        assertThat(member.password()).hasToString("@password1" + "$$$!!@@@");
        assertThat(member.role()).isEqualTo(MemberRole.ROLE_USER);
        assertThat(member.status()).isEqualTo(MemberStatus.ACTIVE);
        assertThat(member.provider()).isEqualTo(OAuthProvider.GOOGLE);
        assertThat(member.providerId()).isEqualTo("google");
    }

    @ParameterizedTest(name = "회원 상태 {index} : {0}")
    @EnumSource(mode = Mode.EXCLUDE, names = {"ACTIVE"})
    void 회원의_상태를_비교하여_일치하지_않으면_TRUE를_반환한다(final MemberStatus status) {
        // given
        final Member member = Member.builder()
                .status(MemberStatus.ACTIVE)
                .build();

        // when
        final boolean result = member.isStatusMismatch(status);

        // then
        assertThat(result).isTrue();
    }

    @ParameterizedTest(name = "회원 상태 {index} : {0}")
    @EnumSource(mode = Mode.EXCLUDE, names = {"ACTIVE"})
    void 회원의_상태를_비교하여_일치하면_FALSE를_반환한다(final MemberStatus status) {
        // given
        final Member member = Member.builder()
                .status(status)
                .build();

        // when
        final boolean result = member.isStatusMismatch(status);

        // then
        assertThat(result).isFalse();
    }

    @ParameterizedTest(name = "회원 상태 {index} : {0}")
    @EnumSource(mode = Mode.EXCLUDE, names = {"INACTIVE"})
    void 회원의_상태_정보를_업데이트한다(final MemberStatus status) {
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
        final Member updateMember = member.update(status);

        // then
        assertThat(updateMember.id()).isEqualTo(1);
        assertThat(updateMember.email()).hasToString("handwoong@gmail.com");
        assertThat(updateMember.password()).hasToString("@password1");
        assertThat(updateMember.phoneNumber()).isNull();
        assertThat(updateMember.role()).isEqualTo(MemberRole.ROLE_USER);
        assertThat(updateMember.status()).isEqualTo(status);
        assertThat(updateMember.provider()).isEqualTo(OAuthProvider.NONE);
        assertThat(updateMember.providerId()).isEqualTo(OAuthProvider.NONE.name());
    }

    @Test
    void 회원의_상태를_INACTIVE로_변경하면_예외가_발생한다() {
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
        // then
        assertThatThrownBy(() -> member.update(MemberStatus.INACTIVE))
                .isInstanceOf(MemberStatusNotValidException.class)
                .hasMessage("변경 불가능한 회원 상태입니다.");
    }

    @ParameterizedTest(name = "소셜 {index} : {0}")
    @EnumSource(mode = Mode.EXCLUDE, names = {"NONE"})
    void 회원의_소셜_로그인_정보를_업데이트한다(final OAuthProvider provider) {
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
        final Member updateMember = member.update(provider, provider.name());

        // then
        assertThat(updateMember.id()).isEqualTo(1);
        assertThat(updateMember.email()).hasToString("handwoong@gmail.com");
        assertThat(updateMember.password()).hasToString("@password1");
        assertThat(updateMember.phoneNumber()).isNull();
        assertThat(updateMember.role()).isEqualTo(MemberRole.ROLE_USER);
        assertThat(updateMember.status()).isEqualTo(MemberStatus.ACTIVE);
        assertThat(updateMember.provider()).isEqualTo(provider);
        assertThat(updateMember.providerId()).isEqualTo(provider.name());
    }

    @ParameterizedTest(name = "휴대폰 번호 {index} : {0}")
    @ValueSource(strings = {"0101112222", "01011112222"})
    void 회원의_휴대폰_번호를_업데이트한다(final String phoneNumber) {
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
        final PhoneNumberUpdate request = PhoneNumberUpdate.builder()
                .phoneNumber(new PhoneNumber(phoneNumber))
                .build();

        // when
        final Member updateMember = member.update(request);

        // then
        assertThat(updateMember.id()).isEqualTo(1);
        assertThat(updateMember.email()).hasToString("handwoong@gmail.com");
        assertThat(updateMember.password()).hasToString("@password1");
        assertThat(updateMember.phoneNumber()).hasToString(phoneNumber);
        assertThat(updateMember.role()).isEqualTo(MemberRole.ROLE_USER);
        assertThat(updateMember.status()).isEqualTo(MemberStatus.ACTIVE);
        assertThat(updateMember.provider()).isEqualTo(OAuthProvider.NONE);
        assertThat(updateMember.providerId()).isEqualTo(OAuthProvider.NONE.name());
    }

    @Test
    void 회원의_비밀번호를_변경한다() {
        // given
        final MemberTestContainer testContainer = new MemberTestContainer();
        final Member member = Member.builder()
                .id(1L)
                .email(new Email("handwoong@gmail.com"))
                .password(new Password("@password1").encode(testContainer.passwordEncoder))
                .phoneNumber(null)
                .role(MemberRole.ROLE_USER)
                .status(MemberStatus.ACTIVE)
                .provider(OAuthProvider.NONE)
                .providerId(OAuthProvider.NONE.name())
                .build();
        final ChangePassword request = ChangePassword.builder()
                .password(new Password("@password1"))
                .newPassword(new Password("!@#pass123"))
                .build();

        // when
        final Member updateMember = member.changePassword(request, testContainer.passwordEncoder);

        // then
        assertThat(updateMember.id()).isEqualTo(1);
        assertThat(updateMember.email()).hasToString("handwoong@gmail.com");
        assertThat(updateMember.password()).hasToString("!@#pass123" + "$$$!!@@@");
        assertThat(updateMember.phoneNumber()).isNull();
        assertThat(updateMember.role()).isEqualTo(MemberRole.ROLE_USER);
        assertThat(updateMember.status()).isEqualTo(MemberStatus.ACTIVE);
        assertThat(updateMember.provider()).isEqualTo(OAuthProvider.NONE);
        assertThat(updateMember.providerId()).isEqualTo(OAuthProvider.NONE.name());
    }

    @Test
    void 회원_비밀번호_변경_시_기존_비밀번호가_일치하지_않으면_예외가_발생한다() {
        // given
        final MemberTestContainer testContainer = new MemberTestContainer();
        final Member member = Member.builder()
                .id(1L)
                .email(new Email("handwoong@gmail.com"))
                .password(new Password("@password1").encode(testContainer.passwordEncoder))
                .phoneNumber(null)
                .role(MemberRole.ROLE_USER)
                .status(MemberStatus.ACTIVE)
                .provider(OAuthProvider.NONE)
                .providerId(OAuthProvider.NONE.name())
                .build();
        final ChangePassword request = ChangePassword.builder()
                .password(new Password("password1234"))
                .newPassword(new Password("!@#pass123"))
                .build();

        // when
        // then
        assertThatThrownBy(() -> member.changePassword(request, testContainer.passwordEncoder))
                .isInstanceOf(PasswordNotMatchedException.class)
                .hasMessage("비밀번호가 일치하지 않습니다.");
    }

    @Test
    void 회원_비밀번호를_새로_초기화한다() {
        // given
        final MemberTestContainer testContainer = new MemberTestContainer();
        final Member member = Member.builder()
                .id(1L)
                .email(new Email("handwoong@gmail.com"))
                .password(new Password("@password1").encode(testContainer.passwordEncoder))
                .phoneNumber(null)
                .role(MemberRole.ROLE_USER)
                .status(MemberStatus.ACTIVE)
                .provider(OAuthProvider.NONE)
                .providerId(OAuthProvider.NONE.name())
                .build();
        final ResetPassword request = ResetPassword.builder()
                .newPassword(new Password("!@#pass123"))
                .build();

        // when
        final Member updateMember = member.resetPassword(request, testContainer.passwordEncoder);

        // then
        assertThat(updateMember.id()).isEqualTo(1);
        assertThat(updateMember.email()).hasToString("handwoong@gmail.com");
        assertThat(updateMember.password()).hasToString("!@#pass123" + "$$$!!@@@");
        assertThat(updateMember.phoneNumber()).isNull();
        assertThat(updateMember.role()).isEqualTo(MemberRole.ROLE_USER);
        assertThat(updateMember.status()).isEqualTo(MemberStatus.ACTIVE);
        assertThat(updateMember.provider()).isEqualTo(OAuthProvider.NONE);
        assertThat(updateMember.providerId()).isEqualTo(OAuthProvider.NONE.name());
    }

    @Test
    void 회원_휴대폰_번호를_삭제한다() {
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
        final Member updateMember = member.deletePhoneNumber();

        // then
        assertThat(updateMember.id()).isEqualTo(1);
        assertThat(updateMember.email()).hasToString("handwoong@gmail.com");
        assertThat(updateMember.password()).hasToString("@password1");
        assertThat(updateMember.phoneNumber()).isNull();
        assertThat(updateMember.role()).isEqualTo(MemberRole.ROLE_USER);
        assertThat(updateMember.status()).isEqualTo(MemberStatus.ACTIVE);
        assertThat(updateMember.provider()).isEqualTo(OAuthProvider.NONE);
        assertThat(updateMember.providerId()).isEqualTo(OAuthProvider.NONE.name());
    }
}
