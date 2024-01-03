package com.handwoong.rainbowletter.member.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatNoException;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.handwoong.rainbowletter.member.exception.PasswordFormatNotValidException;
import com.handwoong.rainbowletter.member.exception.PasswordNotMatchedException;
import com.handwoong.rainbowletter.mock.member.MemberTestContainer;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

class PasswordTest {
    @ParameterizedTest(name = "비밀번호 {index} : {0}")
    @ValueSource(strings = {"password1", "@password1", "pass1234", "@pass123"})
    void 비밀번호의_형식이_옳바르다면_비밀번호를_생성한다(final String password) {
        // given
        // when
        final Password result = new Password(password);

        // then
        assertThat(result.password()).isEqualTo(password);
    }

    @ParameterizedTest(name = "비밀번호 {index} : {0}")
    @NullSource
    void 비밀번호가_null인_경우_예외가_발생한다(final String password) {
        // expect
        assertThatThrownBy(() -> new Password(password))
                .isInstanceOf(PasswordFormatNotValidException.class)
                .hasMessage("비밀번호는 영문, 숫자를 조합하여 8글자 이상으로 입력해주세요.");
    }

    @ParameterizedTest(name = "비밀번호 {index} : {0}")
    @ValueSource(strings = {"password", "@password", "1234", "12345678"})
    void 비밀번호의_형식이_옳바르지_않다면_예외가_발생한다(final String password) {
        // expect
        assertThatThrownBy(() -> new Password(password))
                .isInstanceOf(PasswordFormatNotValidException.class)
                .hasMessage("비밀번호는 영문, 숫자를 조합하여 8글자 이상으로 입력해주세요.");
    }

    @ParameterizedTest(name = "비밀번호 {index} : {0}")
    @ValueSource(strings = {"password1", "@password1", "pass1234", "@pass123"})
    void 비밀번호_암호화에_성공한다(final String password) {
        // given
        final MemberTestContainer testContainer = new MemberTestContainer();
        final Password pwd = new Password(password);

        // when
        final Password encoded = pwd.encode(testContainer.passwordEncoder);

        // then
        assertThat(encoded.password()).isEqualTo(password + "$$$!!@@@");
    }

    @ParameterizedTest(name = "비밀번호 {index} : {0}")
    @ValueSource(strings = {"password1", "@password1", "pass1234", "@pass123"})
    void 암호화된_비밀번호와_평문_비밀번호가_일치_여부_검사에_성공한다(final String password) {
        // given
        final MemberTestContainer testContainer = new MemberTestContainer();
        final Password pwd = new Password(password);
        final Password encoded = pwd.encode(testContainer.passwordEncoder);

        // when
        // then
        assertThatNoException().isThrownBy(() -> encoded.compare(testContainer.passwordEncoder, pwd));
    }

    @ParameterizedTest(name = "비밀번호 {index} : {0}")
    @ValueSource(strings = {"password1", "@password1", "pass1234", "@pass123"})
    void 암호화된_비밀번호와_평문_비밀번호가_일치하지_않으면_예외가_발생한다(final String password) {
        // given
        final MemberTestContainer testContainer = new MemberTestContainer();
        final Password pwd = new Password(password);
        final Password encoded = pwd.encode(testContainer.passwordEncoder);
        final Password invalidPassword = new Password("FAIL" + password);

        // when
        // then
        assertThatThrownBy(() -> encoded.compare(testContainer.passwordEncoder, invalidPassword))
                .isInstanceOf(PasswordNotMatchedException.class)
                .hasMessage("비밀번호가 일치하지 않습니다.");
    }
}
