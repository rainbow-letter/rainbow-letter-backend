package com.handwoong.rainbowletter.domain.member.model;

import static com.handwoong.rainbowletter.util.TestConstants.NEW_EMAIL;
import static com.handwoong.rainbowletter.util.TestConstants.NEW_PASSWORD;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.handwoong.rainbowletter.domain.member.dto.ChangePasswordRequest;
import com.handwoong.rainbowletter.domain.member.dto.ChangePhoneNumberRequest;
import com.handwoong.rainbowletter.domain.member.dto.MemberRegisterRequest;
import com.handwoong.rainbowletter.exception.ErrorCode;
import com.handwoong.rainbowletter.exception.RainbowLetterException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.EnumSource.Mode;
import org.springframework.security.crypto.password.PasswordEncoder;

class MemberTest {
    @Test
    @DisplayName("회원의 비밀번호를 암호화 한다.")
    void encodePassword() {
        // given
        final MemberRegisterRequest registerRequest = new MemberRegisterRequest(NEW_EMAIL, NEW_PASSWORD);
        final Member member = Member.create(registerRequest);

        // when
        member.encodePassword(new FakePasswordEncoder());

        // then
        assertThat(member).extracting("password").isEqualTo(NEW_PASSWORD + "encode");
    }

    @ParameterizedTest
    @EnumSource(mode = Mode.EXCLUDE, names = {"INACTIVE"})
    @DisplayName("회원의 상태를 변경한다.")
    void changeStatus(final MemberStatus status) {
        // given
        final MemberRegisterRequest registerRequest = new MemberRegisterRequest(NEW_EMAIL, NEW_PASSWORD);
        final Member member = Member.create(registerRequest);

        // when
        member.changeStatus(status);

        // then
        assertThat(member).extracting("status").isEqualTo(status);
    }

    @ParameterizedTest
    @EnumSource(mode = Mode.INCLUDE, names = {"INACTIVE"})
    @DisplayName("유효하지 않은 상태로 변경 시 예외가 발생한다.")
    void invalidChangeStatus(final MemberStatus status) {
        // given
        final MemberRegisterRequest registerRequest = new MemberRegisterRequest(NEW_EMAIL, NEW_PASSWORD);
        final Member member = Member.create(registerRequest);

        // expect
        assertThatThrownBy(() -> member.changeStatus(status))
                .isInstanceOf(RainbowLetterException.class)
                .hasMessage(ErrorCode.INVALID_MEMBER_STATUS.getMessage());
    }

    @ParameterizedTest
    @EnumSource(mode = Mode.EXCLUDE, names = {"INACTIVE"})
    @DisplayName("회원의 상태가 전달받은 상태와 불일치 하는지 여부를 반환한다.")
    void statusMismatch(final MemberStatus status) {
        // given
        final MemberRegisterRequest registerRequest = new MemberRegisterRequest(NEW_EMAIL, NEW_PASSWORD);
        final Member member = Member.create(registerRequest);

        // when
        final boolean result = member.isStatusMismatch(status);

        // then
        assertThat(result).isTrue();
    }

    @Test
    @DisplayName("회원의 비밀번호를 변경한다.")
    void changePassword() {
        // given
        final MemberRegisterRequest registerRequest = new MemberRegisterRequest(NEW_EMAIL, NEW_PASSWORD);
        final Member member = Member.create(registerRequest);

        final ChangePasswordRequest changePasswordRequest = new ChangePasswordRequest(NEW_PASSWORD, "changePassword1");

        // when
        member.changePassword(changePasswordRequest, new FakePasswordEncoder());

        // then
        assertThat(member).extracting("password").isEqualTo("changePassword1encode");
    }

    @Test
    @DisplayName("회원 비밀번호 변경 시 기존 비밀번호가 일치하지 않으면 예외가 발생한다.")
    void invalidChangePassword() {
        // given
        final MemberRegisterRequest registerRequest = new MemberRegisterRequest(NEW_EMAIL, NEW_PASSWORD);
        final Member member = Member.create(registerRequest);

        final PasswordEncoder passwordEncoder = new FakePasswordEncoder();
        final ChangePasswordRequest changePasswordRequest =
                new ChangePasswordRequest("invalidPassword1", "changePassword1");

        // expect
        assertThatThrownBy(() -> member.changePassword(changePasswordRequest, passwordEncoder))
                .isInstanceOf(RainbowLetterException.class)
                .hasMessage(ErrorCode.INVALID_PASSWORD.getMessage());
    }

    @Test
    @DisplayName("회원의 휴대폰 번호를 변경한다.")
    void changePhoneNumber() {
        // given
        final ChangePhoneNumberRequest request = new ChangePhoneNumberRequest("01012345678");
        final MemberRegisterRequest registerRequest = new MemberRegisterRequest(NEW_EMAIL, NEW_PASSWORD);
        final Member member = Member.create(registerRequest);

        // when
        member.changePhoneNumber(request);

        // then
        assertThat(member).extracting("phoneNumber").isEqualTo("01012345678");
    }
}
