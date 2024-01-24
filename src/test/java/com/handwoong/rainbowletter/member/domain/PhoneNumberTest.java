package com.handwoong.rainbowletter.member.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.handwoong.rainbowletter.member.exception.PhoneNumberFormatNotValidException;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

class PhoneNumberTest {
    @ParameterizedTest(name = "휴대폰 번호 {index} : {0}")
    @ValueSource(strings = {"0101112222", "01011112222"})
    void 휴대폰_번호의_형식이_옳바르다면_휴대폰_번호를_생성한다(final String phoneNumber) {
        // given
        // when
        final PhoneNumber phoneNum = new PhoneNumber(phoneNumber);

        // then
        assertThat(phoneNum.phoneNumber()).isEqualTo(phoneNumber);
    }

    @ParameterizedTest(name = "휴대폰 번호 {index} : {0}")
    @NullSource
    void 휴대폰_번호가_null인_경우_예외가_발생한다(final String phoneNumber) {
        // expect
        assertThatThrownBy(() -> new PhoneNumber(phoneNumber))
                .isInstanceOf(PhoneNumberFormatNotValidException.class)
                .hasMessage("유효하지 않은 휴대폰 번호 형식입니다.");
    }

    @ParameterizedTest(name = "휴대폰 번호 {index} : {0}")
    @ValueSource(strings = {"0551112222", "010-1111-2222", "0211112222"})
    void 휴대폰_번호의_형식이_옳바르지_않다면_예외가_발생한다(final String phoneNumber) {
        // expect
        assertThatThrownBy(() -> new PhoneNumber(phoneNumber))
                .isInstanceOf(PhoneNumberFormatNotValidException.class)
                .hasMessage("유효하지 않은 휴대폰 번호 형식입니다.");
    }
}
