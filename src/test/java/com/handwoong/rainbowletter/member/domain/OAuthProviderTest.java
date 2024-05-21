package com.handwoong.rainbowletter.member.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import com.handwoong.rainbowletter.member.exception.OAuthProviderTypeNotValidException;

class OAuthProviderTest {
    @ParameterizedTest(name = "소셜 registrationId {index} : {0}")
    @CsvSource(value = {"google, GOOGLE", "naver, NAVER"})
    void OAuth_등록_ID로_Provider를_찾는다(final String registrationId, final OAuthProvider provider) {
        // given
        // when
        final OAuthProvider result = OAuthProvider.match(registrationId);

        // then
        assertThat(result).isEqualTo(provider);
    }

    @ParameterizedTest(name = "소셜 registrationId {index} : {0}")
    @ValueSource(strings = {"github", "facebook"})
    void OAuth_등록_ID가_없는_경우_Provider를_찾지_못하고_예외가_발생한다(final String registrationId) {
        // expect
        assertThatThrownBy(() -> OAuthProvider.match(registrationId))
                .isInstanceOf(OAuthProviderTypeNotValidException.class)
                .hasMessage("유효하지 않은 소셜 로그인 타입입니다.");
    }
}
