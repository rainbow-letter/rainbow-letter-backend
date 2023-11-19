package com.handwoong.rainbowletter.domain.member;

import static com.handwoong.rainbowletter.util.Constants.NEW_EMAIL;
import static com.handwoong.rainbowletter.util.Constants.NEW_PASSWORD;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.EnumSource.Mode;

import com.handwoong.rainbowletter.dto.member.MemberRegisterRequest;
import com.handwoong.rainbowletter.exception.ErrorCode;
import com.handwoong.rainbowletter.exception.RainbowLetterException;

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
        assertThat(member).extracting("password").isEqualTo("encodedPassword");
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
}
