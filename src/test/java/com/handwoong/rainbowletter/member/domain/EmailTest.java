package com.handwoong.rainbowletter.member.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.handwoong.rainbowletter.member.exception.EmailFormatNotValidException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

class EmailTest {
    @Test
    void 이메일의_형식이_옳바르다면_이메일을_생성한다() {
        // given
        final String email = "handwoong@gmail.com";

        // when
        final Email result = new Email(email);

        // then
        assertThat(result.email()).isEqualTo("handwoong@gmail.com");
    }

    @ParameterizedTest(name = "이메일 {index} : {0}")
    @NullSource
    void 이메일이_null인_경우_예외가_발생한다(final String email) {
        // expect
        assertThatThrownBy(() -> new Email(email))
                .isInstanceOf(EmailFormatNotValidException.class)
                .hasMessage("유효하지 않은 이메일 형식입니다.");
    }

    @ParameterizedTest(name = "이메일 {index} : {0}")
    @ValueSource(strings = {"handwoong", "handwoong@", "handwoong@gma", "handwoong@gmail."})
    void 이메일의_형식이_옳바르지_않다면_예외가_발생한다(final String email) {
        // expect
        assertThatThrownBy(() -> new Email(email))
                .isInstanceOf(EmailFormatNotValidException.class)
                .hasMessage("유효하지 않은 이메일 형식입니다.");
    }
}